package com.romantupikov.simpleapp.service;

import com.romantupikov.simpleapp.entity.Account;
import com.romantupikov.simpleapp.security.annotation.HashServiceType;
import com.romantupikov.simpleapp.security.annotation.Sha;
import com.romantupikov.simpleapp.security.control.HashGenerator;
import com.romantupikov.simpleapp.security.entity.Token;
import com.romantupikov.simpleapp.security.entity.TokenType;
import com.romantupikov.simpleapp.security.exception.InvalidUsernameException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;

@Stateless
public class TokenBean implements TokenService {

    @PersistenceContext(unitName = "webapp-persistence-unit")
    private EntityManager em;

    @Inject
    @HashServiceType(HashServiceType.HashType.SHA)
    @Sha(algorithm = Sha.SHAAlgorithm.SHA256)
    private HashGenerator hash;

    @EJB
    private AccountService accountService;

    @Override
    public String generate(String username, String ipAddress, String description,
                           TokenType tokenType) {

        String rawToken = UUID.randomUUID().toString();
        Instant expiration = Instant.now().plus(14, DAYS);

        save(rawToken, username, ipAddress, description, tokenType, expiration);

        return rawToken;
    }

    @Override
    public void save(String rawToken, String username, String ipAddress, String description,
                     TokenType tokenType, Instant expiration) {

        Account account = this.accountService.getByUsername(username)
                .orElseThrow(InvalidUsernameException::new);

        Token token = new Token();

        token.setTokenHash(this.hash.getHashedText(rawToken));
        token.setExpiration(expiration);
        token.setDescription(description);
        token.setTokenType(tokenType);
        token.setIpAddress(ipAddress);

        account.addToken(token);

        this.em.merge(account);
    }

    @Override
    public void remove(String token) {
        this.em.createNamedQuery(Token.REMOVE_TOKEN, Token.class)
                .setParameter("tokenHash", token).executeUpdate();
    }

    @Override
    public void removeExpired() {
        this.em.createNamedQuery(Token.REMOVE_EXPIRED_TOKEN, Token.class).executeUpdate();
    }
}
