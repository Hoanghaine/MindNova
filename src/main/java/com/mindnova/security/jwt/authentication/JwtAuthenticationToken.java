package com.mindnova.security.jwt.authentication;

import org.hibernate.engine.internal.Collections;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private Object principal;
    private Object credentials;
    private final Integer userId;

    public JwtAuthenticationToken(Object credentials) {
        super(null);
        this.userId = null;
        this.credentials = credentials;
    }

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities,Object principal, Object credentials, Integer userId) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.userId = userId;
        setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public Integer getUserId() {
        return userId;
    }
}
