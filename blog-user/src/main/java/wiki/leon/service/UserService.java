package wiki.leon.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiki.leon.dao.UserDao;
import wiki.leon.pojo.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void saveUser(User user, String code){
        String sysCode = (String) redisTemplate.opsForValue().get("email_register_code" + user.getEmail());
        if(sysCode == null){
            throw new RuntimeException("验证码不正确");
        }
        if(!sysCode.equals(code)){
            throw new RuntimeException("验证码不正确");
        }
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        user.setLastLoginTime(date);
        userDao.save(user);
    }

    public void sendEmailCode(String email) {
        // 随机生成一个5位的字符
        String code = RandomStringUtils.random(5);
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("code", code);
        // 调用发送邮件的微服务将验证码发送给用户
        rabbitTemplate.convertAndSend("email.register.code", map);
        // 将验证码保存到redis 30分钟后过期
        redisTemplate.opsForValue().set("email_register_code" + email, code, 30 , TimeUnit.SECONDS);

    }
}
