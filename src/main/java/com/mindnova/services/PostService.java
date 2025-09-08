package com.mindnova.services;

import com.mindnova.dto.PostDto;
import com.mindnova.dto.request.PostFilterDto;
import com.mindnova.dto.request.PostRequestDto;
import com.mindnova.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {

    PostDto createPost(PostRequestDto requestDto);
    Optional<PostDto> getPostById(Integer id);
    Page<PostDto> getAllPosts(Pageable pageable);
    void deletePost(Integer id);
    Page<PostDto> searchPosts(String title, String type , Pageable pageable);
}
