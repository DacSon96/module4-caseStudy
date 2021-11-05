package com.codegym.model.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {
    private Long id;
    private String username;
    private String email;
    private Collection<? extends GrantedAuthority> roles;
    private String token;
    private String type = "Bearer";

    public JwtResponse(String accessToken, Long id, String username, String email, Collection<? extends GrantedAuthority> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
}
