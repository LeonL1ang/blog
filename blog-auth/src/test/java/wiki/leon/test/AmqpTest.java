package wiki.leon.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmqpTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void test(){
        String email = "lianghc@aliyun.com";
        amqpTemplate.convertAndSend("email.send.code", email);
    }
}
