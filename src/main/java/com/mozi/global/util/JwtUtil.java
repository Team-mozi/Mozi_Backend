package com.mozi.global.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final long accessTokenExpirationMillis;
    private final long refreshTokenExpirationMillis;

    public JwtUtil(@Value("${jwt.secret}") String secretKey,
                   @Value("${jwt.access-expiration}") long accessTokenExpirationSeconds,
                   @Value("${jwt.refresh-expiration}") long refreshTokenExpirationSeconds) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpirationMillis = accessTokenExpirationSeconds * 1000;
        this.refreshTokenExpirationMillis = refreshTokenExpirationSeconds * 1000;
    }

    public String createAccessToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenExpirationMillis);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    public String createRefreshToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenExpirationMillis);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}