package com.george.conduit.security.jwt;


import com.george.conduit.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtHelper {

    @Value("${jwt.signing.secret}")
    private String signingKey;

    @Value("${jwt.exp_time_in_millis}")
    private Long expTimeInMillis;

    public String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        Date now = new Date();

        return Jwts.builder()
                .signWith(key)
                .setIssuer("localhost")
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expTimeInMillis)) // 30 minutes from now
                .compact();
    }

    public Claims parseJwt(String compactJws) {
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(compactJws).getBody();
        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid jwt.");
        }
    }

    //Validates that the Subject in the JWT corresponds to an actual User in our database
    //We dont need to check the expiration date since the parseJwt method will handle that
    public Boolean validateToken(Claims tokenClaims, UserDetails userDetails) {
        return userDetails.getUsername().equals(tokenClaims.getSubject());
    }

    public String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
