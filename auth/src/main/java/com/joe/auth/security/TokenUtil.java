package com.joe.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {

    private static final long VALID_PERIOD = 864000L;

    @Value("${auth.secret}")
    private String TOKEN_SECRET;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + VALID_PERIOD * 1000);
    }

}
