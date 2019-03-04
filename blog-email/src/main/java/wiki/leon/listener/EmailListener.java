package wiki.leon.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wiki.leon.utils.EmailUtil;

import java.util.Map;

@Component
@RabbitListener(queues = "email.send.code")
public class EmailListener {

    @Autowired
    private EmailUtil emailUtil;

    @RabbitHandler
    public void sendRegisterCode(Map<String, String> map){
        // 发送短信
        try {
            emailUtil.sendHtmlEmail(map.get("email"), map.get("code"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
