package wiki.leon.zuul.client.filter.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "blog.conf.router")
public class BlogRouterConfig {

    private List<String> defaultUrl;
    private List<String> userLoginUrl;

    public List<String> getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(List<String> defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public List<String> getUserLoginUrl() {
        return userLoginUrl;
    }

    public void setUserLoginUrl(List<String> userLoginUrl) {
        this.userLoginUrl = userLoginUrl;
    }
}
