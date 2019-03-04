package wiki.leon.auth;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiki.leon.auth.dao.UserDao;
import wiki.leon.auth.pojo.User;
import wiki.leon.auth.utils.JwtUtil;
import wiki.leon.blog.common.entity.BlogException;
import wiki.leon.blog.common.entity.ResultStatusEnum;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private JwtUtil jwtUtil;


    public String register(User user, String code) {
        String redisCode = stringRedisTemplate.opsForValue().get("register_code" + user.getEmail());
        if(StringUtils.isNotEmpty(redisCode)){
            if(redisCode.equals(code)){
                user.setProfile("");
                // 前端将md5加密好的密码传到后端再进行一次加密
                user.setPassword(encoder.encode(user.getPassword()));
                Date date = new Date();
                user.setCreateTime(date);
                user.setLoginTime(date);
                user.setUpdateTime(date);
                userDao.save(user);
                // 生成token并返回
                return jwtUtil.createJWT(user.getId().toString(), user.getName(), "user");
            }else {
                throw new BlogException(ResultStatusEnum.VALIDATE_FAIL);
            }
        }else {
            throw new BlogException(ResultStatusEnum.VALIDATE_FAIL);
        }
    }

    public void sendMailCode(String email) {
        if(StringUtils.isNotEmpty(email)){
            // 判断邮箱是否已经注册，已注册就不能发验证码
            if(userDao.existsByEmail(email)){
                throw new BlogException(ResultStatusEnum.DATA_EXISTS);
            }else {
                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                // 生成一个4位的随机code
                String code = RandomStringUtils.randomAlphabetic(4);
                map.put("code", code);
                // 将生成的随机code存到redis，30分钟后失效
                stringRedisTemplate.opsForValue().set("register_code" + email, code ,30 , TimeUnit.SECONDS);
                // 向发送邮件的队列发送一条消息
                amqpTemplate.convertAndSend("email.send.code", map);
            }
        }else {
            throw new BlogException(ResultStatusEnum.PARAM_ERR);
        }
    }

    public String login(String email, String password) {
        User user = userDao.getByEmail(email);
        // 判断用户是否存在且密码是否正确
        if(user != null && encoder.matches(password, user.getPassword())){
            // 生成token并返回
            return jwtUtil.createJWT(user.getId().toString(), user.getName(), "user");
        }else {
            throw new BlogException(ResultStatusEnum.VALIDATE_FAIL);
        }
    }

    // 未做缓存
    public User getUserByToken(String token) {
        Claims claims = jwtUtil.parseJWT(token);
        Long id = Long.parseLong(claims.getId());
        User user = userDao.findById(id).get();
        // 在数据库查询user
        // 缓存起来
        if(user == null){
            throw new BlogException(ResultStatusEnum.NOT_LOGIN);
        }
        return user;
    }
}
