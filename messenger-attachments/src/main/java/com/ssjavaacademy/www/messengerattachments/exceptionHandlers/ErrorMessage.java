package com.ssjavaacademy.www.messengerattachments.exceptionHandlers;

import java.util.Date;

public class ErrorMessage {
    private final Date timestamp;
    private final String message;
    private final String description;

    public ErrorMessage(String message, String description) {
        this.timestamp = new Date();
        this.message = message;
        this.description = description;
    }
    public ErrorMessage(String message) {
        this.timestamp = new Date();
        this.message = message;
        this.description = message;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }
}