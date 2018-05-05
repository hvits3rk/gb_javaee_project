package com.romantupikov.simpleapp.security.service;

import com.romantupikov.simpleapp.entity.Account;
import com.romantupikov.simpleapp.service.AccountService;
import com.romantupikov.simpleapp.service.TokenService;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

import static com.romantupikov.simpleapp.security.entity.TokenType.REMEMBER_ME;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static org.omnifaces.util.Servlets.getRemoteAddr;

@ApplicationScoped
public class SoteriaRememberMeIdentityStore implements RememberMeIdentityStore {

    @Inject
    HttpServletRequest request;

    @EJB
    AccountService accountService;

    @EJB
    TokenService tokenService;


    @Override
    public CredentialValidationResult validate(RememberMeCredential rememberMeCredential) {
        Optional<Account> account = this.accountService.getByLoginToken(rememberMeCredential.getToken(), REMEMBER_ME);

        if (account.isPresent()) {
            return new CredentialValidationResult(new CallerPrincipal(account.get().getUsername()));
        } else {
            return INVALID_RESULT;
        }
    }

    @Override
    public String generateLoginToken(CallerPrincipal callerPrincipal, Set<String> set) {
        return this.tokenService.generate(callerPrincipal.getName(), getRemoteAddr(request), getDescription(), REMEMBER_ME);
    }

    @Override
    public void removeLoginToken(String loginToken) {
        this.tokenService.remove(loginToken);
    }

    private String getDescription() {
        return "Remember me session: " + this.request.getHeader("User-Agent");
    }

}
