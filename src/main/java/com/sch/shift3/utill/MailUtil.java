package com.sch.shift3.utill;

import com.sch.shift3.user.dto.EmailAuthDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class MailUtil {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(EmailAuthDto.EmailRequest mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = null;
        try {
            message = new MimeMessageHelper(mimeMessage, true, "UTF-8");


            message.setTo(mail.getTo());
//        message.setFrom(""); from 값을 설정하지 않으면 application.yml의 username값이 설정됩니다.
            message.setSubject(mail.getTitle());
            StringBuffer emailcontent = new StringBuffer();
            emailcontent.append("<!DOCTYPE html>");
            emailcontent.append("<html>");
            emailcontent.append("<head>");
            emailcontent.append("</head>");
            emailcontent.append("<body>");
            emailcontent.append(
                    MessageFormat.format(" <div\tstyle=\"font-family: ''Apple SD Gothic Neo'', ''sans-serif'' !important; width: 400px; height: 600px; border-top: 4px solid rgb(0, 25, 91); margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">\t<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">\t\t<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">SHIFT3</span><br />\t\t<span style=\"color: rgb(0, 25, 91)\">메일인증</span> 안내입니다.\t</h1>\n\t<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">{0}\t\t\t\t님 안녕하세요.<br />\t\tSHIFT3입니다.<br /> 인증번호는 <a style='font-weight:800;'>{1}</a> 입니다. <br />\t\t감사합니다.\t</p>\t<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div> </div>", mail.getTo(), mail.getText())
            );
            emailcontent.append("</body>");
            emailcontent.append("</html>");

            message.setText(emailcontent.toString(), true);

            mailSender.send(message.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

}
