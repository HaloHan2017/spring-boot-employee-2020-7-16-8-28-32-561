package com.thoughtworks.springbootemployee.handler;

import com.thoughtworks.springbootemployee.enums.ExceptionMessage;
import com.thoughtworks.springbootemployee.exception.CustomException;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage noSuchDataExceptionHandler() {
        return ExceptionMessage.DATA_NOT_FOUND;
    }

    @ExceptionHandler(IllegalOperationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionMessage illegalOperationExceptionHandler() {
        return ExceptionMessage.ILLEGAL_OPERATION;
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String customExceptionHandler(CustomException exception) {
        return exception.getMessage();
    }
}
