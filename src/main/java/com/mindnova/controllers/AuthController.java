package com.mindnova.controllers;

import com.mindnova.common.RoleEnum;
import com.mindnova.dto.request.LoginRequestDTO;
import com.mindnova.dto.request.RegisterRequestDto;
import com.mindnova.entities.Profile;
import com.mindnova.entities.Role;
import com.mindnova.entities.User;
import com.mindnova.repositories.UserRepository;
import com.mindnova.security.jwt.JwtTokenProvider;
import com.mindnova.utils.PasswordEncoderSingleton;
import com.mindnova.utils.PasswordStrengthValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = PasswordEncoderSingleton.getInstance();
    private final PasswordStrengthValidator passwordStrengthValidator = PasswordStrengthValidator.getInstance();

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserDetails userDetails =  userDetailsService.loadUserByUsername(loginRequestDTO.getEmail());

        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if(!userDetails.isEnabled()) {
            throw new RuntimeException("User is disabled");
        }

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getName().name()) // getName() trả RoleEnum, name() trả String
                .toList();
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", user.getId());
        payload.put("email", user.getEmail());
        payload.put("role", roles);

        String token = jwtTokenProvider.generateToken(payload);
        return Map.of("token", token);
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody RegisterRequestDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }

        if (!passwordStrengthValidator.getInstance().isStrong(dto.getPassword())) {
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

        return Map.of(
                "userId", user.getId(),
                "email", user.getEmail(),
                "username", user.getUsername(),
                "message", "Đăng ký thành công. Vui lòng đăng nhập."
        );
    }

}
