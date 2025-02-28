package com.boomzin.subscriptionhub.config.security;

import com.boomzin.subscriptionhub.domain.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Base64;
import java.util.function.Supplier;

public class BasicAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(BasicAuthenticationProvider.class);

    private final UserService userService;

    public BasicAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(BasicAuthentication.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        try {
            if (authentication instanceof BasicAuthentication) {
                return processAuthentication((BasicAuthentication) authentication);
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

    private BasicAuthentication processAuthentication(BasicAuthentication auth) throws AuthenticationException {
        final String base64 = auth.getBase64();
        final Supplier<InvalidTokenAuthenticationException> checkTokenError =
                () -> new InvalidTokenAuthenticationException("base:" + base64);
        String decoded = new String(Base64.getDecoder().decode(base64));
        String[] arr = decoded.split(":");
        String username;
        String password;
        username = arr[0];
        password = arr[1];

        TokenInfo tokenInfo = userService.getTokenInfo(username, password);

        return processInfo(auth, username, tokenInfo);
    }

    private BasicAuthentication processInfo(final BasicAuthentication authentication, String principal,
                                                 final TokenInfo tokenInfo) {

        return new BasicAuthentication(authentication.getBase64(),
                tokenInfo.getPermissions(), true, principal);
    }
}
