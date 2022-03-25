package com.example.demo.Exception;

public class TokenFormateNotValidException extends RuntimeException {

    public String messege;

    public TokenFormateNotValidException(String messege) {
        super(messege);
        this.messege = messege;
    }

    public String getMessege() {
        return messege;
    }
}
