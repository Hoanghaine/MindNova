package com.mindnova.controllers;
import com.mindnova.dto.SocialUserDto;
import com.mindnova.dto.request.LoginRequestDTO;
import com.mindnova.dto.request.RegisterRequestDto;
import com.mindnova.dto.request.SocialLoginRequest;
import com.mindnova.dto.response.ApiResponse;
import com.mindnova.security.CustomUserDetails;
import com.mindnova.services.AuthService;
import com.mindnova.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }
    @PostMapping("/social-login")
    public ApiResponse<Map<String, Object>> socialLogin(
            @RequestParam("login-type") String provider,
            @RequestBody SocialLoginRequest request
    ) {
        return authService.socialLogin(provider, request.getIdToken());
    }


    @PostMapping("/register")
    public ApiResponse<Map<String, Object>> register(@RequestBody RegisterRequestDto dto) {
        return authService.register(dto);
    }
    @GetMapping("/me")
    public ApiResponse<Map<String, Object>> getCurrentUser(Authentication authentication) {
        return authService.me(authentication);
    }


}
