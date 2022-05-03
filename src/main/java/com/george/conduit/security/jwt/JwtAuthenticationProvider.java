package com.george.conduit.security.jwt;

import com.george.conduit.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        Claims parsedJwt = jwtHelper.parseJwt(token);
        String jwtSubject = parsedJwt.getSubject();

        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(jwtSubject);

        MyPrincipal myPrincipal = new MyPrincipal(userDetails.getUser().getId(),
                userDetails.getUser().getProfile().getProfileName());

        if (jwtHelper.validateToken(parsedJwt, userDetails)) {
            return new JwtAuthentication(userDetails.getAuthorities(), myPrincipal, token);
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthentication.class);
    }
}
