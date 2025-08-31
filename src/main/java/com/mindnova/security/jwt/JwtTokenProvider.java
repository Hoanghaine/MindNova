package com.mindnova.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider implements TokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    @Override
    public String generateToken(Map<String, Object> payload) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        return JWT.create()
                .withIssuer("mindnova-app")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .withPayload(payload)
                .sign(algorithm);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("mindnova-app")
                    .build();

            DecodedJWT jwt = verifier.verify(token); // throws if invalid/expired
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    @Override
    public Map<String, Object> getPayload(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("mindnova-app")
                .build();

        DecodedJWT jwt = verifier.verify(token);

        Map<String, Object> payload = new java.util.HashMap<>();
        jwt.getClaims().forEach((key, claim) -> {
            if (claim.asString() != null) {
                payload.put(key, claim.asString());
            } else if (claim.asBoolean() != null) {
                payload.put(key, claim.asBoolean());
            } else if (claim.asInt() != null) {
                payload.put(key, claim.asInt());
            } else if (claim.asLong() != null) {
                payload.put(key, claim.asLong());
            } else if (claim.asDate() != null) {
                payload.put(key, claim.asDate());
            } else {
                payload.put(key, claim.toString());
            }
        });

        return payload;
    }

}
