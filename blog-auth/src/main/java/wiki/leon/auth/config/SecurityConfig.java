package wiki.leon.auth.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 重写父类的授权规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        // 定制请求授权规则
        http.authorizeRequests()
                .antMatchers("/").permitAll();  // 允许所有人访问
                //.antMatchers("/user/getUser").hasRole("user");  // 该路径只能user角色访问

        // 开启登录功能，如果不满足上面的授权规则就会跳转到security生成的登录页面
        http
        .csrf().disable();  // 关闭csrf功能
    }
}
