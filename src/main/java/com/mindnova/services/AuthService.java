package com.mindnova.services;

import com.mindnova.dto.request.LoginRequestDTO;
import com.mindnova.dto.request.RegisterRequestDto;
import com.mindnova.dto.request.SocialLoginRequest;
import com.mindnova.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface AuthService {
    ApiResponse<Map<String, Object>> login(LoginRequestDTO loginRequestDTO);
    ApiResponse<Map<String, Object>> register(RegisterRequestDto dto);
    ApiResponse<Map<String, Object>> me(Authentication authentication);
    ApiResponse<Map<String, Object>> socialLogin(String provider, String idToken);
}
