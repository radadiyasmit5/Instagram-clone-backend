package com.example.demo.payload;

import lombok.Data;

@Data
public class JwttokenResponse {

    private String token;
    private String username;
}
