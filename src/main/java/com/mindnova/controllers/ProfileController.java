package com.mindnova.controllers;

import com.mindnova.dto.ProfileDto;
import com.mindnova.dto.response.ApiResponse;
import com.mindnova.services.AuthService;
import com.mindnova.services.ProfileService;
import com.mindnova.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final JwtUtils jwtUtils;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<ProfileDto>> getMyProfile(@RequestHeader("Authorization") String authHeader) {

        Integer userId =  jwtUtils.extractAccountIdFromAuthHeader(authHeader);

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
