package com.example.demo.payload;


import java.util.Date;

public class ErrorDetails {

    public Date timestamp;
    public String messege;
    public String details;

    public ErrorDetails(Date timestamp, String messege, String details) {
        this.timestamp = timestamp;
        this.messege = messege;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessege() {
        return messege;
    }

    public String getDetails() {
        return details;
    }
}
