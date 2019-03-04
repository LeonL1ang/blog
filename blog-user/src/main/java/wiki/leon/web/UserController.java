package wiki.leon.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wiki.leon.blog.common.entity.Result;
import wiki.leon.blog.common.entity.ResultStatusEnum;
import wiki.leon.pojo.User;
import wiki.leon.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/{code}")
    public Result register(@RequestBody User user, @PathVariable String code){
        System.out.println(user.getEmail());
        System.out.println(code);
        userService.saveUser(user, code);
        return Result.to(ResultStatusEnum.OK);
    }

    @PostMapping("/sendEmailCode/{email}")
    public Result sendEmailCode(@PathVariable String email){
        userService.sendEmailCode(email);
        return Result.to(ResultStatusEnum.OK);
    }

}
