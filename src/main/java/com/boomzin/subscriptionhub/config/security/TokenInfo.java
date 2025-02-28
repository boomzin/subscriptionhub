package com.boomzin.subscriptionhub.config.security;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class TokenInfo {
    private final String login;
    private final List<String> permissions;

    public TokenInfo(String login, List<String> permissions) {
        this.login = login;
        this.permissions = permissions == null ? Collections.emptyList() : permissions;
    }

}
