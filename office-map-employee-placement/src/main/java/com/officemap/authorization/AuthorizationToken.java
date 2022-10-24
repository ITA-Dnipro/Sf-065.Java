package com.officemap.authorization;

import javax.persistence.*;

@Entity
@Table(name = "tokens")
public class AuthorizationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tokenId;
    private String tokenValue;

    public AuthorizationToken() {
    }

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }
}
