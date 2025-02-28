package com.boomzin.subscriptionhub.config.security;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenAuthenticationException extends AuthenticationException {

    public InvalidTokenAuthenticationException(String token) {
        super("Invalid token " + token);
    }
}
