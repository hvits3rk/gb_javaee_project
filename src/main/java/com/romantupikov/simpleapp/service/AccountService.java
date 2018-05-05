package com.romantupikov.simpleapp.service;

import com.romantupikov.simpleapp.entity.Account;
import com.romantupikov.simpleapp.security.entity.TokenType;

import javax.ejb.Local;
import java.util.Optional;

@Local
public interface AccountService {

    void registerAccount(final String username, final String email, final String password);

    Account deleteAccountByEmail(String email);

    Optional<Account> getByUsername(final String username);

    Optional<Account> getByEmail(final String email);

    Optional<Account> getByLoginToken(String loginToken, TokenType tokenType);

    Account getByUsernameAndPassword(String username, String password);
}
