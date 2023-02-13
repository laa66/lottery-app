package com.laa66.springmvc.lottery.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class FormErrorException extends RuntimeException {

    public FormErrorException(String message) {
        super(message);
    }
}
