package com.mindnova.services.impl;

import com.mindnova.dto.RoleDto;
import com.mindnova.dto.UserDto;
import com.mindnova.repositories.UserRepository;
import com.mindnova.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

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
}
