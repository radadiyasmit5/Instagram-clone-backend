package com.example.demo.service.impl;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailSenderService {

    public void sendMail(String toMail, String otvc) throws MessagingException, UnsupportedEncodingException;
}
