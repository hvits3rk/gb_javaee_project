package com.romantupikov.simpleapp.security.service;

import com.romantupikov.simpleapp.entity.Account;
import com.romantupikov.simpleapp.security.exception.AccountNotVerifiedException;
import com.romantupikov.simpleapp.security.exception.InvalidCredentialException;
import com.romantupikov.simpleapp.service.AccountService;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

@ApplicationScoped
public class SoteriaIdentityStore implements IdentityStore {

    @EJB
    private AccountService accountService;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        try {

            // check if the credential was UsernamePasswordCredential
            if (credential instanceof UsernamePasswordCredential) {
                String username = ((UsernamePasswordCredential) credential).getCaller();
                String password = ((UsernamePasswordCredential) credential).getPasswordAsString();

                return validate(this.accountService.getByUsernameAndPassword(username, password));
            }

            // check if the credential was CallerOnlyCredential
            if (credential instanceof CallerOnlyCredential) {
                String username = ((CallerOnlyCredential) credential).getCaller();

                return validate(
                        this.accountService.getByUsername(username)
                                .orElseThrow(InvalidCredentialException::new)
                );
            }

        } catch (InvalidCredentialException e) {
            return INVALID_RESULT;
        }
        return NOT_VALIDATED_RESULT;
    }

    // before return the CredentialValidationResult, check if the account is active or not
    private CredentialValidationResult validate(Account account) {
        if (!account.getActive()) {
            throw new AccountNotVerifiedException();
        }

        return new CredentialValidationResult(account.getUsername());
    }
}
