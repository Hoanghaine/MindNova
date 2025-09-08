package com.mindnova.repositories;

import com.mindnova.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByPostIdAndUserId(Integer postId, Integer userId);
    Long countByPostId(Integer postId);
}
