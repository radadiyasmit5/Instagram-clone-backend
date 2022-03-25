package com.example.demo.payload;

import lombok.Data;

import java.util.Date;

@Data
public class SignupDto {

    private String fullname;
    private String email;
    private String username;
    private String password;
    private String phone;
    private Date birthdate;
}
