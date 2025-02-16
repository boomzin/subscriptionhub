package com.boomzin.subscriptionhub.common.exception;

public class UnauthorizedException extends DomainException {
    public UnauthorizedException() {
        super(ResponseStatus.NOT_AUTHENTICATED, "Unauthorized");
    }
}
