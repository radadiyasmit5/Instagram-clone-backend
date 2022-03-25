package com.example.demo.payload;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class LoginDto {

    private String username;
    private String password;

}
