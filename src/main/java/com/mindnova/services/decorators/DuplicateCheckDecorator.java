package com.mindnova.services.decorators;

import com.mindnova.dto.PostDto;
import com.mindnova.dto.request.PostRequestDto;
import com.mindnova.entities.Post;
import com.mindnova.repositories.PostRepository;
import com.mindnova.services.PostService;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class DuplicateCheckDecorator extends PostServiceDecorator {

    private final PostRepository postRepository;
    private final ExecutorService executor = Executors.newFixedThreadPool(4); // => tao thread pool voi 4 thread == 1 lan chay 4 task -- thua se thanh hang cho queue

    public DuplicateCheckDecorator(PostService delegate, PostRepository postRepository) {
        super(delegate);
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostRequestDto requestDto) {
        String newContent = (requestDto.getTitle() + " " + requestDto.getContent()).toLowerCase();

        List<Post> existingPosts = postRepository.findAll();

        // chay song song kiem tra trung
        List<CompletableFuture<Double>> futures = existingPosts.stream()
                .map(post -> CompletableFuture.supplyAsync(() -> {
                    String existing = (post.getTitle() + " " + post.getContent()).toLowerCase();
                    return calculateSimilarity(newContent, existing);
                }, executor))
                .collect(Collectors.toList());

        // join cho ket qua va duyet
        for (CompletableFuture<Double> f : futures) {
            double similarity = f.join();
            if (similarity >= 0.8) {
                throw new RuntimeException("Bài viết trùng lặp với bài đã có (similarity=" + similarity + ")");
            }
        }

        return delegate.createPost(requestDto);
    }

    /**
     * Tính độ tương đồng dựa trên Jaccard Similarity (token-based).
     */
    private double calculateSimilarity(String s1, String s2) {
        var set1 = List.of(s1.split("\\s+")).stream().collect(Collectors.toSet());
        var set2 = List.of(s2.split("\\s+")).stream().collect(Collectors.toSet());

        long intersection = set1.stream().filter(set2::contains).count();
        long union = set1.size() + set2.size() - intersection;

        return union == 0 ? 0.0 : (double) intersection / union;
    }
}
