package wiki.leon.zuul.client.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import wiki.leon.blog.common.entity.Result;
import wiki.leon.blog.common.entity.ResultStatusEnum;
import wiki.leon.zuul.client.filter.client.config.BlogRouterConfig;
import wiki.leon.zuul.feign.UserTokenClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestHeaderFilter extends ZuulFilter {

    @Autowired
    private BlogRouterConfig routerConfig;

    @Autowired
    private UserTokenClient userTokenClient;

    // jackson的序列化类
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 得到request上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 得到request
        HttpServletRequest request = currentContext.getRequest();

        // 如果请求的是OPTIONS方法直接放行
        if(request.getMethod().equals(RequestMethod.OPTIONS.name())){
            return null;
        }

        // 如果请求的路径不是需要登录的路径 直接放行
        if(!routerConfig.getUserLoginUrl().contains(request.getRequestURI())){
            return null;
        }

        // 获取请求的token头信息
        String token = request.getHeader("token");
        if(StringUtils.isNotEmpty(token)){
            // 判断token的合法性
            Boolean pass = userTokenClient.checkUser(token);
            // 通过放行
            if(pass){
                // 把token放入header中交给微服务处理
                currentContext.addZuulRequestHeader("token", token);
            }else {
                currentContext.setSendZuulResponse(false);
            }
            return null;
            // currentContext.setRequest(request);
        }
        // 拒接访问
        HttpServletResponse response = currentContext.getResponse();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        try {
            // 设置返回body
            currentContext.setResponseBody(objectMapper.writeValueAsString(Result.to(ResultStatusEnum.NOT_LOGIN)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        currentContext.setSendZuulResponse(false);  //false  不会继续往下执行 不会调用服务接口了 网关直接响应给客户了
        return null;
    }
}
