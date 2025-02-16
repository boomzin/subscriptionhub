package com.boomzin.subscriptionhub.common.exception;


import org.springframework.http.HttpStatus;

public class PromptDomainException extends DomainException {

    public PromptDomainException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }
}
