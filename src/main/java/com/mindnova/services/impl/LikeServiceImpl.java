package com.mindnova.services.impl;

import com.mindnova.entities.Like;
import com.mindnova.repositories.LikeRepository;
import com.mindnova.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;

    @Override
    public Like addLike(Like like) {
        Optional<Like> existing = likeRepository.findByPostIdAndUserId(
                like.getPost().getId(), like.getUser().getId());
        if (existing.isPresent()) {
            return existing.get(); // đã like rồi
        }
        like.setCreatedAt(java.time.LocalDateTime.now());
        return likeRepository.save(like);
    }

    @Override
    public void removeLike(Integer postId, Integer userId) {
        Optional<Like> existing = likeRepository.findByPostIdAndUserId(postId, userId);
        existing.ifPresent(likeRepository::delete);
    }

    @Override
    public Long countLikes(Integer postId) {
        return likeRepository.countByPostId(postId);
    }

    @Override
    public Optional<Like> getLike(Integer postId, Integer userId) {
        return likeRepository.findByPostIdAndUserId(postId, userId);
    }
}
