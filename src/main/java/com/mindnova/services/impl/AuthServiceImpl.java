package com.mindnova.services.impl;

import com.google.gson.Gson;
import com.mindnova.dto.SocialUserDto;
import com.mindnova.dto.request.LoginRequestDTO;
import com.mindnova.dto.request.RegisterRequestDto;
import com.mindnova.dto.request.SocialLoginRequest;
import com.mindnova.dto.response.ApiResponse;
import com.mindnova.dto.response.MeResponseDto;
import com.mindnova.entities.Profile;
import com.mindnova.entities.User;
import com.mindnova.factories.SocialLoginAdapterFactory;
import com.mindnova.repositories.UserRepository;
import com.mindnova.security.CustomUserDetails;
import com.mindnova.security.CustomUserDetailsService;
import com.mindnova.services.AuthService;
import com.mindnova.services.UserService;
import com.mindnova.utils.JwtUtils;
import com.mindnova.utils.PasswordStrengthValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordStrengthValidator passwordStrengthValidator = PasswordStrengthValidator.getInstance();
    private final SocialLoginAdapterFactory socialLoginAdapterFactory;
    private final UserService userService;

    @Override
    public ApiResponse<Map<String, Object>> login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );


        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();


        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT
        String jwt = jwtUtils.generateToken(userDetails, user.getId());

        Map<String,Object> data = Map.of("token",jwt);

        return new ApiResponse<>(
                true,
                data,
                "Đăng nhập thành công",
                null
        );
    }


    @Override
    public ApiResponse<Map<String, Object>> register(RegisterRequestDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }

        if (!passwordStrengthValidator.isStrong(dto.getPassword())) {
            throw new RuntimeException("Mật khẩu yếu");
        }

        User user = User.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        userRepository.save(user);

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setAvatar("https://example.com/default-avatar.png");

        user.setProfile(profile);
        userRepository.save(user);

        return new ApiResponse<>(
                true,
                null,
                "Đăng ký thành công. Vui lòng đăng nhập.",
                null
        );
    }

    @Override
    public ApiResponse<Map<String, Object>> me(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        var roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        MeResponseDto userResponse = new MeResponseDto(
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getUsername(),
                roles
        );

        Map<String, Object> data = Map.of("user", userResponse);

        return new ApiResponse<>(
                true,
                data,
                "Lấy thông tin thành công.",
                null
        );
    }

    @Override
    public ApiResponse<Map<String, Object>> socialLogin(String provider, String idToken) {
        // 1. Xác thực với adapter
        SocialUserDto socialUser = socialLoginAdapterFactory
                .getAdapter(provider)
                .authenticate(idToken);

        // 2. Kiểm tra user trong DB
        User user = userRepository.findByEmail(socialUser.getEmail())
                .orElseGet(() -> userService.registerSocialUser(socialUser));


        // 3. Convert sang CustomUserDetails để dùng với JWT
        CustomUserDetails userDetails = new CustomUserDetails(user);

        // 4. Sinh JWT
        String jwtAccessToken = jwtUtils.generateToken(userDetails, user.getId());

        // 5. Trả response
        Map<String, Object> data = Map.of(
                "accessToken", jwtAccessToken
        );

        return new ApiResponse<>(true, data, "Social login successful", null);
    }


}
