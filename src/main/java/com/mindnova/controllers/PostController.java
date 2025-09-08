package com.mindnova.controllers;

import com.mindnova.dto.PostDto;
import com.mindnova.dto.request.PostRequestDto;
import com.mindnova.dto.response.ApiResponse;
import com.mindnova.services.PostService;
import com.mindnova.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final JwtUtils jwtUtils;
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostRequestDto requestDto, @RequestHeader("Authorization") String authHeader ) {
        requestDto.setAuthorId(  jwtUtils.extractAccountIdFromAuthHeader(authHeader));
        PostDto created = postService.createPost(requestDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/search")
    public Page<PostDto> searchPosts(@RequestParam(required = false)  String title, @RequestParam(required = false)  String type, Pageable pageable) {
        return postService.searchPosts(title, type, pageable);
    }

    @GetMapping("/{id}")
    public Optional<PostDto> getPost(@PathVariable Integer id) {
        return postService.getPostById(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostDto>>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostDto> postDtoPage = postService.getAllPosts(PageRequest.of(page, size));

        ApiResponse<List<PostDto>> response = new ApiResponse<>(
                true,
                postDtoPage.getContent(),
                "Fetched posts successfully",
                new ApiResponse.Pagination(
                        postDtoPage.getNumber(),
                        postDtoPage.getSize(),
                        postDtoPage.getTotalElements(),
                        postDtoPage.getTotalPages()
                )
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
    }
}
