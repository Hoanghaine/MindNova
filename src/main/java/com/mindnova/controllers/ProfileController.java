package com.mindnova.controllers;

import com.mindnova.dto.ProfileDto;
import com.mindnova.dto.response.ApiResponse;
import com.mindnova.security.jwt.authentication.JwtAuthenticationToken;
import com.mindnova.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<ProfileDto>> getMyProfile(Authentication authentication) {

        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
        Integer userId = jwtToken.getUserId();

        ProfileDto profileDto = profileService.getMyProfile(userId);

        ApiResponse<ProfileDto> response = new ApiResponse<>(
                true,
                profileDto,
                "Fetched profile successfully",
                null
        );

        return ResponseEntity.ok(response);
    }
}
