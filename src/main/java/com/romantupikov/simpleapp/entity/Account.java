package com.romantupikov.simpleapp.entity;

import com.google.gson.annotations.Expose;
import com.romantupikov.simpleapp.security.entity.Token;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "email"})
})
@NamedQueries({
        @NamedQuery(name = Account.FIND_BY_USERNAME, query = "select a from Account a where a.username = :username"),
        @NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email"),
        @NamedQuery(name = Account.FIND_BY_TOKEN, query = "select a from Account a inner join a.tokens t where t.tokenHash = :tokenHash and t.tokenType = :tokenType and t.expiration > CURRENT_TIMESTAMP")
})
public class Account implements Serializable {

    public static final String FIND_BY_USERNAME = "Account.findByUsername";
    public static final String FIND_BY_EMAIL = "Account.findByEmail";
    public static final String FIND_BY_TOKEN = "Account.findByToken";

    @Expose
    @Id
    @GeneratedValue(generator = "account_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq", allocationSize = 1)
    private Long id;

    @Expose
    @NotNull
    @Size(min = 1, max = 100)
    private String username;

    @NotNull
    private String password;

    @Expose
    @NotNull
    @Size(min = 1, max = 100)
    private String email;

    @NotNull
    private Boolean active;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens = new ArrayList<>();

    public Account() {
    }

    public Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @PrePersist
    public void generateInformation() {
        setActive(true);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean publish) {
        this.active = publish;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void addToken(Token token) {
        this.tokens.add(token);
        token.setAccount(this);
    }

    public void removeToken(Token token) {
        this.tokens.remove(token);
        token.setAccount(this);
    }

    @Override
    public String toString() {
        return "Account: " + this.username;
    }
}
