package com.mindnova.adapter;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.mindnova.dto.SocialUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class GoogleLoginAdapter implements SocialLoginAdapter {

    private final GoogleIdTokenVerifier verifier;

    public GoogleLoginAdapter(
            @Value("${spring.security.oauth2.client.registration.google.client-id}") String clientId
    ) {
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    @Override
    public SocialUserDto authenticate(String accessToken) {
        GoogleIdToken.Payload payload = verifyGoogleToken(accessToken);

        return new SocialUserDto(
                payload.getSubject(),            // Google user ID
                payload.getEmail(),              // Email
                (String) payload.get("name"),    // Tên hiển thị
                (String) payload.get("picture"), // Avatar
                "GOOGLE"
        );
    }

    private GoogleIdToken.Payload verifyGoogleToken(String token) {
        try {
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                return idToken.getPayload();
            } else {
                throw new RuntimeException("Invalid Google ID token");
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Failed to verify Google ID token", e);
        }
    }

    @Override
    public String getProviderName() {
        return "GOOGLE";
    }
}
