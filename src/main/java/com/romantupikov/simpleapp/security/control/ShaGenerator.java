package com.romantupikov.simpleapp.security.control;

import com.romantupikov.simpleapp.security.annotation.HashServiceType;

import javax.ejb.Stateless;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Stateless
@HashServiceType(HashServiceType.HashType.SHA)
public class ShaGenerator implements HashGenerator {

    public String algorithmName;

    protected ShaGenerator() {}

    public ShaGenerator(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    @Override
    public String getHashedText(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(this.algorithmName);
            byte[] hash = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getEncoder().encodeToString(hash);

            return encoded;
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean isHashedTextMatch(String text, String hashedText) {
        String tempHashedText = getHashedText(text);
        return tempHashedText.equals(hashedText);
    }
}
