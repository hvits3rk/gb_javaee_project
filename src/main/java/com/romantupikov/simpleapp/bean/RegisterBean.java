package com.romantupikov.simpleapp.bean;

import com.romantupikov.simpleapp.service.AccountService;

import javax.ejb.EJB;
import javax.enterprise.inject.Model;

import static org.omnifaces.util.Messages.addGlobalInfo;
import static org.omnifaces.util.Faces.redirect;

@Model
public class RegisterBean {

    private String username;
    private String password;
    private String email;

    @EJB
    private AccountService accountService;

    public void submit() {
        this.accountService.registerAccount(username, email, password);
        addGlobalInfo("register.message.success");
        redirect("/login.xhtml");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
