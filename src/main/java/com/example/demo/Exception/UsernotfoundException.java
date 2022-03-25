package com.example.demo.Exception;

public class UsernotfoundException extends RuntimeException {


    private String messege;

    public UsernotfoundException(String messege) {
        super(messege);
        this.messege = messege;
    }

    public String getUsername() {
        return messege;
    }
}


