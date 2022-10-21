package com.example.employeemanagementauth.exeptions;


import org.springframework.mail.MailException;

public class AuthException extends RuntimeException {
    public AuthException(String s, MailException e) {
        super(s, e);
    }

    public AuthException(String s) {
        super(s);
    }
}
