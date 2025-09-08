package com.mindnova.controllers;

import com.mindnova.entities.Like;
import com.mindnova.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public Like addLike(@RequestBody Like like) {
        return likeService.addLike(like);
    }

    @DeleteMapping
    public void removeLike(@RequestParam Integer postId, @RequestParam Integer userId) {
        likeService.removeLike(postId, userId);
    }

    @GetMapping("/count/{postId}")
    public Long countLikes(@PathVariable Integer postId) {
        return likeService.countLikes(postId);
    }

    @GetMapping
    public Optional<Like> getLike(@RequestParam Integer postId, @RequestParam Integer userId) {
        return likeService.getLike(postId, userId);
    }
}
