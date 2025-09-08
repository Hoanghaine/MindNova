package com.mindnova.services.decorators;

import com.mindnova.dto.PostDto;
import com.mindnova.dto.request.PostRequestDto;
import com.mindnova.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public abstract class PostServiceDecorator implements PostService {
    protected final PostService delegate;

    protected PostServiceDecorator(PostService delegate) {
        this.delegate = delegate;
    }

    @Override
    public abstract PostDto createPost(PostRequestDto requestDto);


    @Override
    public Optional<com.mindnova.dto.PostDto> getPostById(Integer id) {
        return delegate.getPostById(id);
    }

    @Override
    public Page<com.mindnova.dto.PostDto> getAllPosts(Pageable pageable) {
        return delegate.getAllPosts(pageable);
    }

    @Override
    public Page<com.mindnova.dto.PostDto> searchPosts(String title, String type, Pageable pageable) {
        return delegate.searchPosts(title, type, pageable);
    }


    @Override
    public void deletePost(Integer id) {
        delegate.deletePost(id);
    }
}
