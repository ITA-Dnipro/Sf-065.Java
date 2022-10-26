package com.ssjavaacademy.www.messengerattachments.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage missingRequestHeaderException(MissingRequestHeaderException ex, WebRequest request) {
        return new ErrorMessage("Authorization token is empty", request.getDescription(false));
    }

    @ExceptionHandler(EmptyTokenException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage emptyTokenException(EmptyTokenException ex, WebRequest request) {
        return new ErrorMessage("Authorization token is empty", request.getDescription(false));
    }

    @ExceptionHandler({MessageNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage messageNotFoundException(MessageNotFoundException ex, WebRequest request) {
        return new ErrorMessage(ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage fileNotFoundException(FileNotFoundException ex, WebRequest request) {
        return new ErrorMessage(ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    public ErrorMessage maxUploadSizeExceededException(MaxUploadSizeExceededException ex, WebRequest request) {
        return new ErrorMessage("File Size Exceeded", request.getDescription(false));
    }

    @ExceptionHandler(NotValidJsonBodyException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage notValidJsonBodyException(NotValidJsonBodyException ex, WebRequest request) {
        return new ErrorMessage(ex.getMessage(), request.getDescription(false));
    }
}
