package com.boomzin.subscriptionhub.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AllAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public AllAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = checkAuthentication(header);
        if (authentication != null && !authentication.isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private Authentication checkAuthentication(String header) throws AuthenticationException {
        if (header.toLowerCase().startsWith("bearer ")) {
            String token = header.substring(7);
            TokenAuthentication tokenAuthentication = new TokenAuthentication(token);
            return getAuthenticationManager().authenticate(tokenAuthentication);
        } else if (header.toLowerCase().startsWith("basic ")) {
            String base64 = header.substring(6);
            BasicAuthentication basicAuthentication = new BasicAuthentication(base64);
            return getAuthenticationManager().authenticate(basicAuthentication);
        }

        return null;
    }

    private AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }
}
