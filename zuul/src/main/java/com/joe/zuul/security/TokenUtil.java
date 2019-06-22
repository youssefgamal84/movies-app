package com.joe.zuul.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {

    @Value("${auth.secret}")
    private String TOKEN_SECRET;

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getSubject();
        } catch (Exception ex) {
            return null;
        }
    }

    private Claims getClaims(String token) {
        Claims claims;

        claims = Jwts.parser().setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();


        return claims;
    }

    public boolean isTokenValid(String token) {
        String username = getUsernameFromToken(token);
        if (username != null) {
            return !isTokenExpired(token);
        } else return false;
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }


}
