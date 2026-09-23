package com.example.springsecurity.security;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
    
    private final SecretKey key;
    private final long expirationMs;

    public JwtService(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.expiration-ms}") long expirationMs
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }
    
    public String generateToken(Long userId) {
        Date current = new Date();
        Date expiry = new Date(current.getTime() + expirationMs);

        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(current)
            .expiration(expiry)
            .signWith(key)
            .compact();
    }

    public Long extractUserId(String token) {
        return Long.parseLong(
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject()
        );
    }
}
