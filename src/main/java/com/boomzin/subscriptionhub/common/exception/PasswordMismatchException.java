package com.boomzin.subscriptionhub.common.exception;

import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends DomainException {
    public PasswordMismatchException() {
        super(HttpStatus.BAD_REQUEST.value(), "invalid password");
    }
}
