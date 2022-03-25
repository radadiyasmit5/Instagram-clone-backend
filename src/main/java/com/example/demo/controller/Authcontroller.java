package com.example.demo.controller;

import com.example.demo.Exception.UsernotfoundException;
import com.example.demo.Security.CustomUserDetailsService;
import com.example.demo.payload.OtvcValidationRequest;
import com.example.demo.utils.Jwtutilsproviders;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.SignupDto;
import com.example.demo.payload.SignupResponseDTO;
import com.example.demo.service.impl.impl.AuthenticationAndRegisterServiceimpl;
import com.example.demo.utils.Otvcutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class Authcontroller {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customeuserDetailsService;

    @Autowired
    private Jwtutilsproviders jwtutilsproviders;

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
    public String userLogin(@RequestBody LoginDto logindto) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logindto.getUsername(), logindto.getPassword()));

        } catch (BadCredentialsException e) {
            throw new UsernotfoundException("Sorry, your password was incorrect. Please double-check your password. ");
        } catch (Exception e) {
//            throw new Exception(e.getMessage());
            if (e.getMessage() == "user not found") {
                throw new UsernotfoundException("The username you entered doesn't belong to an account. Please check your username and try again.");

            } else {
                throw new UsernotfoundException("there is some problem while authenticating user info please try to enter your username and password again");
            }
        }

        UserDetails userdetails = customeuserDetailsService.loadUserByUsername(logindto.getUsername());
        String token = jwtutilsproviders.generateToken(userdetails);
        return token;
    }


}
