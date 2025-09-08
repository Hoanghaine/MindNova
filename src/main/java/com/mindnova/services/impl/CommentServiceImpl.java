package com.mindnova.services.impl;

import com.mindnova.entities.Comment;
import com.mindnova.repositories.CommentRepository;
import com.mindnova.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment createComment(Comment comment) {
        comment.setCreatedAt(java.time.LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getCommentById(Integer id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getCommentsByPostId(Integer postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Comment updateComment(Integer id, Comment comment) {
        return commentRepository.findById(id).map(existing -> {
            existing.setContent(comment.getContent());
            return commentRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Comment not found with id " + id));
    }

    @Override
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }
}
