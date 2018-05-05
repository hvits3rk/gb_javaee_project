package com.romantupikov.simpleapp.service;

import com.romantupikov.simpleapp.entity.Account;
import com.romantupikov.simpleapp.interceptor.Logger;
import com.romantupikov.simpleapp.security.annotation.HashServiceType;
import com.romantupikov.simpleapp.security.annotation.Sha;
import com.romantupikov.simpleapp.security.control.HashGenerator;
import com.romantupikov.simpleapp.security.entity.TokenType;
import com.romantupikov.simpleapp.security.exception.InvalidEmailException;
import com.romantupikov.simpleapp.security.exception.InvalidPasswordException;
import com.romantupikov.simpleapp.security.exception.InvalidUsernameException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Stateless
@Interceptors(Logger.class)
public class AccountBean implements AccountService {

    @PersistenceContext(unitName = "webapp-persistence-unit")
    private EntityManager em;

    @Inject
    @HashServiceType(HashServiceType.HashType.SHA)
    @Sha(algorithm = Sha.SHAAlgorithm.SHA256)
    private HashGenerator tokenHash;

    @Inject
    @HashServiceType(HashServiceType.HashType.SHA)
    @Sha(algorithm = Sha.SHAAlgorithm.SHA512)
    private HashGenerator passwordHash;

    @EJB
    private TokenService tokenService;

    @Override
    public void registerAccount(String username, String email, String password) {
        String securedPassword = this.passwordHash.getHashedText(password);

        Account account = new Account(username, securedPassword, email);

        // Account should not activated by default.
        account.setActive(true);

        this.em.persist(account);
    }

    @Override
    public Account deleteAccountByEmail(String email) {

        Account accountToDelete = getByEmail(email).orElseThrow(InvalidEmailException::new);

        this.em.remove(accountToDelete);

        return accountToDelete;
    }

    @Override
    public Optional<Account> getByUsername(String username) {
        try {
            return Optional.of(
                    this.em.createNamedQuery(Account.FIND_BY_USERNAME, Account.class)
                            .setParameter("username", username).getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Account> getByEmail(String email) {
        try {
            return Optional.of(
                    this.em.createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
                            .setParameter("email", email).getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Account> getByLoginToken(String loginToken, TokenType tokenType) {
        try {
            return Optional.of(
                    this.em.createNamedQuery(Account.FIND_BY_TOKEN, Account.class)
                            .setParameter("tokenHash", this.tokenHash.getHashedText(loginToken))
                            .setParameter("tokenType", tokenType)
                            .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Account getByUsernameAndPassword(String username, String password) {
        Account managedAccount = getByUsername(username).orElseThrow(InvalidUsernameException::new);

        String hashesPassword = this.passwordHash.getHashedText(password);

        if (!hashesPassword.equals(managedAccount.getPassword())) {
            System.out.println(hashesPassword);
            System.out.println(managedAccount.getPassword());
            throw new InvalidPasswordException();
        }

        return managedAccount;
    }
}
