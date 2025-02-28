package com.boomzin.subscriptionhub.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class RSASignature {
    private final PrivateKey privateKey;

    public RSASignature(@Value("${security.private-key}") String pemPrivateKey) throws Exception {
        this.privateKey = loadPrivateKeyFromPEM(pemPrivateKey);
    }

    private PrivateKey loadPrivateKeyFromPEM(String pemKey) throws Exception {
        // Убираем заголовки и переносы строк
        String privateKeyPEM = pemKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", ""); // Удаляем пробелы и переносы строк

        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    public String signData(String data) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signature.sign());
    }
}