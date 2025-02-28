package com.boomzin.subscriptionhub.config.security;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TokenAuthentication implements Authentication {
    @Getter
    private String token;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean isAuthenticated;
    private String principal;

    public TokenAuthentication(String token) {
        this.token = token;
    }

    public TokenAuthentication(String token, List<String> authorities,
                               boolean isAuthenticated, String principal) {
        this.token = token;
        this.authorities = authorities == null ?
                Collections.emptyList() :
                authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        this.isAuthenticated = isAuthenticated;
        this.principal = principal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return principal;
    }

}
