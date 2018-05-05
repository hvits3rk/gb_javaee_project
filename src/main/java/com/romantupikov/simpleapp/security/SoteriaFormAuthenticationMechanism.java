package com.romantupikov.simpleapp.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.*;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AutoApplySession // For "Is user already logged-in?"
@RememberMe(
        cookieMaxAgeSeconds = 60 * 60 * 24 * 14, // 14 days
        cookieSecureOnly = false, // Remove this when login is served over HTTPS.
        isRememberMeExpression = "#{self.isRememberMe(httpMessageContext)}"
)
@LoginToContinue(
        loginPage = "/login.xhtml?continue=true", // specify your login url
        errorPage = "",
        useForwardToLogin = false
)
@ApplicationScoped
public class SoteriaFormAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    private IdentityStore identityStore;


    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext) throws AuthenticationException {

        Credential credential = httpMessageContext.getAuthParameters().getCredential();

        if (credential != null) {
            return httpMessageContext.notifyContainerAboutLogin(this.identityStore.validate(credential));
        } else {
            return httpMessageContext.doNothing();
        }
    }

    // this was called on @RememberMe annotations
    public Boolean isRememberMe(HttpMessageContext httpMessageContext) {
        return httpMessageContext.getAuthParameters().isRememberMe();
    }

    // Workaround for Weld bug; at least in Weld 2.3.2 default methods are not intercepted
    @Override
    public void cleanSubject(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        HttpAuthenticationMechanism.super.cleanSubject(request, response, httpMessageContext);
    }
}
