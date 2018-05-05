package com.romantupikov.simpleapp.security.control;

import com.romantupikov.simpleapp.security.annotation.HashServiceType;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.HashMap;
import java.util.Map;

@Stateless
@HashServiceType(HashServiceType.HashType.PBKDF)
public class PbkdfGenerator implements HashGenerator {

    @Inject
    Pbkdf2PasswordHash pbkdfHash;

    @PostConstruct
    public void init() {
        Map<String, String> parameters = new HashMap<>();

        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");

        this.pbkdfHash.initialize(parameters);
    }

    @Override
    public String getHashedText(String text) {
        return this.pbkdfHash.generate(text.toCharArray());
    }

    @Override
    public boolean isHashedTextMatch(String text, String hashedText) {
        return this.pbkdfHash.verify(text.toCharArray(), hashedText);
    }
}
