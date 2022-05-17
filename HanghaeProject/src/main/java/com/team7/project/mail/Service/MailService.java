package com.team7.project.mail.Service;

import com.team7.project.advice.RestException;
import com.team7.project.mail.template.MailTemplate;
import com.team7.project.mail.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@Slf4j
@Service
@RequiredArgsConstructor
public class MailService implements EmailUtils{


    private final JavaMailSender sender;
    private MailTemplate mailTemplate = new MailTemplate();

    @Override
    public RestException sendEmail(String toEmail, String token, String nickname){
        RestException result = new RestException(null,null);
        try{

            File f1 = new File("");
             MimeMessage message = sender.createMimeMessage();
             MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            String body=mailTemplate.getTemplate(token,toEmail,nickname);
            helper.setTo(toEmail);
            helper.setSubject("WILL_BE : 이메일 인증을 완료해 주세요\uD83D\uDE18");
            helper.setText(body,true);

            FileDataSource fileDataSource = new FileDataSource("./logo.png");

            helper.addInline("logo",fileDataSource);
            sender.send(message);
            result.setMessage("메일 발송 성공");
            result.setHttpStatus(HttpStatus.OK);
        }catch (MessagingException e){
            e.printStackTrace();
            result.setMessage("메일 발송 실패");
            result.setHttpStatus(HttpStatus.BAD_REQUEST);
        }


        return result;
    }
}
