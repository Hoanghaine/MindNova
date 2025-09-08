package com.mindnova.services;

import com.mindnova.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment createComment(Comment comment);
    Optional<Comment> getCommentById(Integer id);
    List<Comment> getCommentsByPostId(Integer postId);
    Comment updateComment(Integer id, Comment comment);
    void deleteComment(Integer id);
}
