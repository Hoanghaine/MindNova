package com.mindnova.services.impl;

import com.mindnova.common.AuthProviderEnum;
import com.mindnova.common.RoleEnum;
import com.mindnova.dto.RoleDto;
import com.mindnova.dto.SocialUserDto;
import com.mindnova.dto.UserDto;
import com.mindnova.entities.Profile;
import com.mindnova.entities.Role;
import com.mindnova.entities.User;
import com.mindnova.repositories.RoleRepository;
import com.mindnova.repositories.UserRepository;
import com.mindnova.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map( user -> UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .roles(
                                user.getRoles().stream()
                                .map( role ->  new RoleDto( role.getId(), role.getName().name()))
                                .collect(Collectors.toSet())
                        )
                        .createdAt(user.getCreatedAt())
                        .build()
                );
    }

    @Override
    public User registerSocialUser(SocialUserDto socialUser) {
        // Generate username từ email
        String baseUsername = socialUser.getEmail().split("@")[0];
        String uniqueUsername = baseUsername + "_" + UUID.randomUUID().toString().substring(0, 6);

        // Lấy role mặc định
        Role defaultRole = roleRepository.findByName(RoleEnum.USER)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        // Tạo User
        User user = new User();
        user.setUsername(uniqueUsername);
        user.setEmail(socialUser.getEmail());
        user.setPassword(null); // không có password khi login social
        user.setRoles(Set.of(defaultRole));
        user.setProvider(AuthProviderEnum.valueOf(socialUser.getProvider())); // GOOGLE, FACEBOOK
        user.setCreatedAt(LocalDateTime.now());

        // Tạo Profile kèm theo
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setAvatar(socialUser.getAvatarUrl());

        // Tách tên (nếu có)
        if (socialUser.getName() != null && socialUser.getName().contains(" ")) {
            String[] parts = socialUser.getName().split(" ", 2);
            profile.setFirstName(parts[0]);
            profile.setLastName(parts[1]);
        } else {
            profile.setFirstName(socialUser.getName());
        }

        profile.setCreatedAt(LocalDateTime.now());

        user.setProfile(profile);

        // Lưu user (cascade sẽ lưu luôn profile)
        return userRepository.save(user);
    }


}
