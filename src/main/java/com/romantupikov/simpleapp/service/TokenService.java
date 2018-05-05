package com.romantupikov.simpleapp.service;

import com.romantupikov.simpleapp.security.entity.TokenType;

import javax.ejb.Local;
import java.time.Instant;

@Local
public interface TokenService {

    String generate(final String username, final String ipAddress, final String description,
                    final TokenType tokenType);

    void save(final String rawToken, final String username, final String ipAddress,
              final String description, final TokenType tokenType, final Instant expiration);

    void remove(String token);

    void removeExpired();
}
