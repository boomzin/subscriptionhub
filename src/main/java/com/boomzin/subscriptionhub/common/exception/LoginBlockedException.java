package com.boomzin.subscriptionhub.common.exception;


import static com.boomzin.subscriptionhub.common.exception.ResponseStatus.LOGIC_ERROR;

public class LoginBlockedException extends DomainException {

    public LoginBlockedException() {
        super(LOGIC_ERROR, "LOGIN_BLOCKED");
    }
}
