package com.mindnova.repositories;

import com.mindnova.dto.request.PostFilterDto;
import com.mindnova.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer> {
//    @Query("SELECT p FROM Post p JOIN FETCH p.author a JOIN FETCH a.profile")
//    Page<Post> findAllWithAuthorProfile(Pageable pageable);
//
    @Query(value ="SELECT p.* FROM post p JOIN [user] u ON p.author_id = u.id JOIN profile pf ON u.id = pf.user_id",
    countQuery = "SELECT COUNT(*) FROM post p JOIN [user] u ON p.author_id = u.id JOIN profile pf ON u.id = pf.user_id",
    nativeQuery = true)
    Page<Post> findAllWithAuthorProfile(Pageable pageable);
}
