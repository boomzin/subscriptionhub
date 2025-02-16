package com.boomzin.subscriptionhub.common.exception;

public class DataConsistencyException extends DomainException {
    public DataConsistencyException(String message) {
        super(ResponseStatus.BAD_REQUEST, message);
    }
}