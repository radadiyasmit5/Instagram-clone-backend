package com.example.demo.controller;

import com.example.demo.payload.LoginDto;
import com.example.demo.payload.OtvcValidationRequest;
import com.example.demo.payload.SignupDto;
import com.example.demo.payload.SignupResponseDTO;
import com.example.demo.service.impl.impl.AuthenticationAndRegisterServiceimpl;
import com.example.demo.utils.Otvcutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class Authcontroller {



    @Autowired
    private Otvcutils otvcutils;

    @Autowired
    AuthenticationAndRegisterServiceimpl signupimplservice;


    /*** registering user ***/
    @PostMapping("/register")
    public ResponseEntity<SignupResponseDTO> userSignup(@RequestBody SignupDto signupDto) {


        SignupResponseDTO response = signupimplservice.userSignup(signupDto);
        return new ResponseEntity(response, HttpStatus.OK);

    }

    /*** Checking if user exists by username or email or phone ***/

    @GetMapping("existby_fieldname/{fieldvalue}")
    public ResponseEntity<?> userExistcheck(@PathVariable(name = "fieldvalue") String fieldvalue) {
        boolean userExist = signupimplservice.existbyusernameoremailorphone(fieldvalue);

        return new ResponseEntity(userExist, HttpStatus.OK);


    }

    /*** generate otvc and sends mail ***/

    @GetMapping("/{emailorphone}")
    public ResponseEntity<String> generateotvc_and_sendmail(@PathVariable(name = "emailorphone") String emailorphone) throws MessagingException, UnsupportedEncodingException {

        return new ResponseEntity(otvcutils.generateOtvc(emailorphone), HttpStatus.OK);
    }

    /*** Validate otvc ***/
    @PostMapping("/validateOTVC")
    public boolean validateotvc(@RequestBody OtvcValidationRequest otvcValidationRequest) {

        return otvcutils.validateOtvc(otvcValidationRequest.getOtvc(), otvcValidationRequest.getEmailorphone());

    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody LoginDto logindto) {

      return  new ResponseEntity(signupimplservice.userLogin(logindto),HttpStatus.OK);
    }


}
