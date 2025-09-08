package com.mindnova.services.impl;

import com.mindnova.common.PostTagEnum;
import com.mindnova.dto.AuthorDto;
import com.mindnova.dto.PostDto;
import com.mindnova.dto.request.PostFilterDto;
import com.mindnova.dto.request.PostRequestDto;
import com.mindnova.entities.Post;
import com.mindnova.entities.User;
import com.mindnova.repositories.PostRepository;
import com.mindnova.repositories.UserRepository;
import com.mindnova.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Override
    public PostDto createPost(PostRequestDto requestDto) {
        // 1. Lấy author
        User author = userRepository.findById(requestDto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        // 2. Tạo entity Post
        Post post = new Post();
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        post.setType(requestDto.getType());
        post.setAuthor(author);
        post.setCreatedAt(LocalDateTime.now());

        // 3. Lưu vào DB
        Post saved = postRepository.save(post);

        // 4. Map sang DTO
        return mapToDto(saved);
    }


    @Override
    public Optional<PostDto> getPostById(Integer id) {
        return postRepository.findById(id)
                .map(this::mapToDto);
    }

    @Override
    public Page<PostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAllWithAuthorProfile(pageable)
                .map(this::mapToDto);
    }

    @Override
    public Page<PostDto> searchPosts(String title, String type , Pageable pageable) {
        Post probe = new Post();

        if (title != null && !title.isEmpty()) {
            probe.setTitle(title);
        }
        if (type != null && !type.isEmpty()) {
            probe.setType(PostTagEnum.valueOf(type.toUpperCase()));
        }

        probe.setIsDeleted(false);
        probe.setCreatedAt(null);

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withMatcher("title", m -> m.contains().ignoreCase());

        Example<Post> example = Example.of(probe, matcher);

        return postRepository.findAll(example, pageable).map(this::mapToDto);
    }

    @Override
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }



    private PostDto mapToDto(Post post) {

        // map AuthorDto
        AuthorDto authorDto = null;
        if (post.getAuthor() != null && post.getAuthor().getProfile() != null) {
            authorDto = AuthorDto.builder()
                    .avatar(post.getAuthor().getProfile().getAvatar())
                    .username(post.getAuthor().getUsername())
                    .build();
        }

        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .type(post.getType())
                .createdAt(post.getCreatedAt())
                .author(authorDto)
                .commentCount(post.getComments() != null ? post.getComments().size() : 0)
                .likeCount(post.getLikes() != null ? post.getLikes().size() : 0)
                .build();
    }

}
