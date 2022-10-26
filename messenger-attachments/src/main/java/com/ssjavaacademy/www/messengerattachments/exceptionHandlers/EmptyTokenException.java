package com.ssjavaacademy.www.messengerattachments.exceptionHandlers;

import java.util.Objects;

public class EmptyTokenException extends Exception {
    public EmptyTokenException(String message) {
        super(message);
    }

    public static void isTokenEmpty(String authorization) throws EmptyTokenException {
        if (Objects.equals(authorization, null) || authorization.isEmpty()) {
            throw new EmptyTokenException("Token is empty");
        }
    }
}
