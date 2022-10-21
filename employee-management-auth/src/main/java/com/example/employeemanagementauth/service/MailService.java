package com.example.employeemanagementauth.service;

import com.example.employeemanagementauth.model.NotificationEmail;


public interface MailService {
    void sendMail(NotificationEmail please_activate_your_account);
}
