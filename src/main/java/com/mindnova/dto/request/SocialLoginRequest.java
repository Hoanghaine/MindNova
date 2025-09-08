package com.mindnova.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocialLoginRequest {
    private String provider;    // GOOGLE, FACEBOOK
    private String idToken; // Google ID Token
}
