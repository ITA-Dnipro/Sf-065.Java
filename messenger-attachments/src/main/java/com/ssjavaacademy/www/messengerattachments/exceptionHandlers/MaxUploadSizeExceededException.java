package com.ssjavaacademy.www.messengerattachments.exceptionHandlers;

public class MaxUploadSizeExceededException extends Exception {
    public MaxUploadSizeExceededException(String message) {
        super(message);
    }
}
