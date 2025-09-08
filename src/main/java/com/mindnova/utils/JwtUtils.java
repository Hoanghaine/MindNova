package com.mindnova.utils;

import com.mindnova.security.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecretString;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    private Key jwtSecretKey;

    @PostConstruct
    public void init() {
        jwtSecretKey = Keys.hmacShaKeyFor(jwtSecretString.getBytes());
    }

    public String generateToken(CustomUserDetails userDetails, Integer accountId) {
        var roles = userDetails.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .toList();

        return Jwts.builder()
                .setSubject(userDetails.getEmail()) // email hoặc username
                .claim("roles", roles)                 // lưu danh sách roles
                .claim("accountId", accountId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            System.err.println("JWT validation error: " + e.getMessage());
            return false;
        }
    }
    public Integer getAccountIdFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("accountId", Integer.class);
    }

    public Integer extractAccountIdFromAuthHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }
        String token = authHeader.replace("Bearer ", "");
        return getAccountIdFromJwt(token);
    }
    // Lấy email trực tiếp từ JWT
    public String getEmailFromJwt(String token) {
        return getUsernameFromJwt(token); // Subject là email
    }

    // Hoặc từ header Authorization
    public String extractEmailFromAuthHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }
        String token = authHeader.replace("Bearer ", "");
        return getEmailFromJwt(token);
    }
}
