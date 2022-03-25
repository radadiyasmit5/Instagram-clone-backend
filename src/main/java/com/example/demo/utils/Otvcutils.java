package com.example.demo.utils;

import com.example.demo.Exception.UsernotfoundException;
import com.example.demo.entity.Otvc;
import com.example.demo.repository.OtvcRepository;
import com.example.demo.service.impl.impl.MailsenderServiceimpl;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class Otvcutils {


    @Autowired
    private OtvcRepository otvcRepository;
    @Autowired
    private MailsenderServiceimpl mailsenderimpl;


    //generate otvc
    public String generateOtvc(String emailorphone) throws MessagingException, UnsupportedEncodingException {


        String otvc = RandomString.make(6);

        if (otvcRepository.existsByemailorphone(emailorphone).orElse(false)) {

            Otvc existingOtvc = otvcRepository.findByemailorphone(emailorphone).orElseThrow(()->new UsernotfoundException("user not exist in otvc repository"));
            existingOtvc.setOtvc(otvc);
            existingOtvc.setCreatedAt(new Date());
            otvcRepository.save(existingOtvc);
        } else {
            Otvc otvcentity = new Otvc();
            otvcentity.setOtvc(otvc);
            otvcentity.setEmailorphone(emailorphone);
            otvcentity.setCreatedAt(new Date());
            otvcRepository.save(otvcentity);
        }
        mailsenderimpl.sendMail(emailorphone, otvc);

        return "otvc has been successfully sent to " + emailorphone;


    }

    //validate otvc
    public boolean validateOtvc(String otvc, String emailorphone) {
        Otvc foundUser = otvcRepository.findByemailorphone(emailorphone).orElseThrow(()->new UsernotfoundException("user not dound in otvc repository by emailorphone: "+emailorphone));

/*** time when user generated otvc ***/

        if (foundUser.getCreatedAt().getTime() + 5 * 60 * 1000 > new Date().getTime()) {
            return true;
        } else {
            return false;
        }

    }
}
