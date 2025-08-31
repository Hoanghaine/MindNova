package com.mindnova.controllers;

import com.mindnova.dto.UserDto;
import com.mindnova.dto.response.ApiResponse;
import com.mindnova.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserDto> userPage = userService.getUsers(PageRequest.of(page,size));
        ApiResponse<List<UserDto>> response = new ApiResponse<>(
                true,
                userPage.getContent(),
                "Fetched users successfully",
                new ApiResponse.Pagination(
                        userPage.getNumber(),
                        userPage.getSize(),
                        userPage.getTotalElements(),
                        userPage.getTotalPages()
                )
        );
        return ResponseEntity.ok(response);
    }
}
