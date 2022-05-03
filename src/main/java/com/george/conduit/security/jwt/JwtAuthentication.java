package com.george.conduit.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

//Store MyPrincipal which contains the userId and username of the successfully authenticated user
public class JwtAuthentication extends AbstractAuthenticationToken {

    private final MyPrincipal principal;
    private final String token;

    public JwtAuthentication(Collection<? extends GrantedAuthority> authorities, MyPrincipal principal, String token) {
        super(authorities);
        this.setAuthenticated(true);
        this.principal = principal;
        this.token = token;
    }

    public JwtAuthentication(String token) {
        super(null);
        this.setAuthenticated(false);
        this.principal = null;
        this.token = token;
    }

    @Override
    public MyPrincipal getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}