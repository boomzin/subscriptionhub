package com.boomzin.subscriptionhub.config.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Sha256PasswordEncoder extends MessageDigestPasswordEncoder {
    public Sha256PasswordEncoder() {
        super("SHA-256");
    }

    public String encodePassword(String rawPass, Object salt) {
        return DigestUtils.sha256Hex(String.format("%s{%s}", rawPass, salt.toString()));
    }
}
