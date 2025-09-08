package com.mindnova.services.proxy;

import com.mindnova.dto.PostDto;
import com.mindnova.dto.request.PostRequestDto;
import com.mindnova.entities.Post;
import com.mindnova.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Slf4j
public class LoggingProxy implements PostService {
    private final PostService delegate;

    public LoggingProxy(PostService delegate) {
        this.delegate = delegate;
    }

    @Override
    public PostDto createPost(PostRequestDto requestDto) {
        log.info("[POST] User {} is creating a post with title='{}'",
                requestDto.getAuthorId(), requestDto.getTitle());
        PostDto result = delegate.createPost(requestDto);
        log.info("[POST] Post created successfully with id={}", result.getId());
        return result;
    }

    @Override
    public void deletePost(Integer id) {
        log.info("[POST] Deleting post id={}", id);
        delegate.deletePost(id);
        log.info("[POST] Post deleted successfully id={}", id);
    }


    @Override
    public Optional<PostDto> getPostById(Integer id) {
        return delegate.getPostById(id);
    }

    @Override
    public Page<PostDto> getAllPosts(Pageable pageable) {
        return delegate.getAllPosts(pageable);
    }

    @Override
    public Page<PostDto> searchPosts(String title, String type, Pageable pageable) {
        return delegate.searchPosts(title, type, pageable);
    }

}
