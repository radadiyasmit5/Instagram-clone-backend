package com.example.demo.payload;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTo {

    private long id;
    private String fullname;
    private String email;
    private String username;
    private String password;
    private String phone;
    private Date birthdate;
    private Date createdAt;
    private Date updatedAt;
}
