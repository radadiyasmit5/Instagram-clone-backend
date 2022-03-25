package com.example.demo.service.impl;

import com.example.demo.payload.JwttokenResponse;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.SignupDto;
import com.example.demo.payload.SignupResponseDTO;

public interface AuthenticationAndRegisterService {

    public SignupResponseDTO userSignup(SignupDto signupDto);

    public boolean existbyusernameoremailorphone(String fieldvalue);

    public JwttokenResponse userLogin (LoginDto loginDto);
}
