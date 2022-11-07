package com.example.employeemanagementauth.exeptions;

import org.springframework.mail.MailException;

public class DepartmentNotFoundException  extends RuntimeException {
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
