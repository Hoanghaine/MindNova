package com.mindnova.controllers;

import com.mindnova.dto.PostDto;
import com.mindnova.dto.UserDto;
import com.mindnova.dto.request.PostFilterDto;
import com.mindnova.dto.response.ApiResponse;
import com.mindnova.entities.Post;
import com.mindnova.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
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

    @PatchMapping("/{id}")
    public Post updatePost(@PathVariable Integer id, @RequestBody Post post) {
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
    }
}
