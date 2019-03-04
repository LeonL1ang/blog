package wiki.leon.zuul.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "blog-auth")
public interface UserTokenClient {

    @RequestMapping(method = RequestMethod.GET, value = "/token/user")
    Boolean checkUser(@RequestParam("token") String token);

}
