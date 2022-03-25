package com.example.demo.payload;

import lombok.Data;

@Data
public class OtvcValidationRequest {
    private String otvc;
    private String emailorphone;
}
