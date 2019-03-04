package wiki.leon.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    // 发送普通邮件
    public void sendEmail(){
        // 简单的邮件发送对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件标题
        message.setSubject("通知-今晚开会");
        // 设置邮件内容
        message.setText("开会的时间是7：30");
        // 接收这地址
        message.setTo("921027074@qq.com");
        // 设置邮件发送者
        message.setFrom("Leon Blog");
        // 发送邮件
        mailSender.send(message);
    }
    // 发送带附件的email
    public void sendHtmlEmail(String toEmail, String code) throws Exception {

        // 创建mimeMessageHelper对象
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置为true代表要发送文件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        // 设置邮件标题
        helper.setSubject("Leon Blog 验证");
        // 设置邮件内容这个模式可以发送html格式的邮件
        // 设置为true 表示发送的邮件是html格式
        helper.setText("<div>您的邮箱验证码为：<p style='color:red'>" + code + "</p><p>请尽快验证，有效期为30分钟。</p></div>", true);
        // 设置发送的文件名字和 new 一个 File
        // helper.addAttachment("file.jpg", new file(path));
        // 接收这地址
        helper.setTo(toEmail);
        // 设置邮件发送者
        helper.setFrom("921027074@qq.com");
        // 发送邮件
        mailSender.send(mimeMessage);
    }
}
