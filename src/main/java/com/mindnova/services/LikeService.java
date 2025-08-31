package com.mindnova.services;

import com.mindnova.entities.Like;

import java.util.Optional;

public interface LikeService {
    Like addLike(Like like);
    void removeLike(Integer postId, Integer userId);
    Long countLikes(Integer postId);
    Optional<Like> getLike(Integer postId, Integer userId);
}
