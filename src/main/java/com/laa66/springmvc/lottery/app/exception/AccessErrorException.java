package com.laa66.springmvc.lottery.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessErrorException extends RuntimeException {
    public AccessErrorException(String message) {
        super(message);
    }
}
