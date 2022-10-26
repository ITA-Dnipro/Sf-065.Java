package com.ssjavaacademy.www.messengerattachments.exceptionHandlers;

public class NotValidJsonBodyException extends Exception{
    public NotValidJsonBodyException(String message) {
            super(message);
    }
}

