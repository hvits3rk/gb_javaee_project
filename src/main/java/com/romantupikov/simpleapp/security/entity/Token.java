package com.romantupikov.simpleapp.security.entity;

import com.romantupikov.simpleapp.entity.Account;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.MONTHS;

@Entity
@Table(name = "token", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"token_hash"})
})
@NamedQueries({
        @NamedQuery(name = Token.REMOVE_TOKEN, query = "delete from Token t where t.tokenHash = :tokenHash"),
        @NamedQuery(name = Token.REMOVE_EXPIRED_TOKEN, query = "delete from Token t where t.expiration < CURRENT_TIMESTAMP")
})
public class Token implements Serializable {

    public static final String REMOVE_TOKEN = "Token.removeToken";
    public static final String REMOVE_EXPIRED_TOKEN = "Token.removeExpiredToken";

    @Id
    @GeneratedValue(generator = "token_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "token_id_seq", sequenceName = "token_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "token_hash")
    private String tokenHash;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(name = "ip_address")
    @Size(min = 1, max = 45)
    private String ipAddress;

    private String description;

    private Instant created;

    private Instant expiration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public Token() {
    }

    @PrePersist
    public void generateInformation() {
        this.created = Instant.now();
        if (this.expiration == null) {
            this.expiration = this.created.plus(1, MONTHS);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Token token = (Token) obj;
        return Objects.equals(id, token.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "Token{ id " + id + '}';
    }
}
