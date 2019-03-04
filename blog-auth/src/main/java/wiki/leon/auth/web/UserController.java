package wiki.leon.auth.web;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import wiki.leon.auth.UserService;
import wiki.leon.auth.pojo.User;
import wiki.leon.blog.common.entity.Result;
import wiki.leon.blog.common.entity.ResultStatusEnum;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register/{code}")
    public Result register(@RequestBody User user, @PathVariable String code){
        String token = userService.register(user, code);
        return Result.to(ResultStatusEnum.OK, token);
    }

    @PostMapping("/sendMailCode/{email}")
    public Result sendMailCode(@PathVariable String email){
        userService.sendMailCode(email);
        return Result.to(ResultStatusEnum.OK);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        String token = userService.login(user.getEmail(), user.getPassword());
        return Result.to(ResultStatusEnum.OK, token);
    }

    @GetMapping("/getInfo")
    public Result getUser(@RequestHeader("token") String token){
        User user = userService.getUserByToken(token);
        return Result.to(ResultStatusEnum.OK, user);
    }

}
