package com.example.demo.service.impl.impl;

import com.example.demo.service.impl.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailsenderServiceimpl implements MailSenderService {


    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(String toMail, String otvc) throws MessagingException, UnsupportedEncodingException {


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

        String body = "<h1>your OTVC for your email validation is " + otvc +"</h1>";
        String subject = "Email varification";
        message.setFrom("No.reply2012@gmail.com","Instagram Registration");
        message.setTo(toMail);
        message.setText(body,true);
        message.setSubject(subject);

        mailSender.send(mimeMessage);

    }


}


