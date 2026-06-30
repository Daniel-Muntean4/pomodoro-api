package com.Daniel_Muntean4.pomodoro_api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
@Service
public class JwtService {
    private final SecretKey secretKey;

    public JwtService(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String subject, List<String> permissions) {
        Date now  = new Date();
        long expirationMs = 60_000* 60;
        return Jwts.builder()
                .subject(subject)
                .claim("permissions", permissions)
                .issuedAt(now)
                .expiration(new Date(now.getTime()+ expirationMs))
                .signWith(secretKey)
                .compact();
    }
    public Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                // exp time is ok and the signature is ok
                .getPayload();
    }
}


