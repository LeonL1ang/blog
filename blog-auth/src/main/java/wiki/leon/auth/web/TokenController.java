package wiki.leon.auth.web;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wiki.leon.auth.utils.JwtUtil;


@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/user")
    public Boolean checkUser(String token){
        if(jwtUtil.parseJWT(token) == null) return false;
        return true;
    }

}
