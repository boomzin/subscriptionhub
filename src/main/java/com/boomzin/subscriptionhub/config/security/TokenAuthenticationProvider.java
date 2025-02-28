package com.boomzin.subscriptionhub.config.security;

import com.boomzin.subscriptionhub.domain.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationProvider.class);
    private final UserService userService;

    public TokenAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(TokenAuthentication.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        try {
            if (authentication instanceof TokenAuthentication) {
                return processAuthentication((TokenAuthentication) authentication);
            } else {
                authentication.setAuthenticated(false);
                return authentication;
            }
        } catch (AuthenticationServiceException e) {
            throw e;
        } catch (Exception e) {
            log.debug("Error when try authenticate ", e);
            authentication.setAuthenticated(false);
            return authentication;
        }
    }

    private TokenAuthentication processAuthentication(TokenAuthentication auth) throws AuthenticationException {
        TokenInfo tokenInfo = userService.getTokenInfo(auth.getToken());

        return processTokenInfo(auth, tokenInfo);
    }

    private TokenAuthentication processTokenInfo(final TokenAuthentication authentication,
                                                 final TokenInfo tokenInfo) {
        return new TokenAuthentication(authentication.getToken(),
                tokenInfo.getPermissions(), true, tokenInfo.getLogin());
    }
}
