package com.mindnova.services.decorators;

import com.mindnova.dto.PostDto;
import com.mindnova.dto.request.PostRequestDto;
import com.mindnova.services.PostService;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ProfanityFilterDecorator extends PostServiceDecorator {
    private final List<String> bannedWords;

    public ProfanityFilterDecorator(PostService delegate,String filePath) {
        super(delegate);
        this.bannedWords = loadBadWords(filePath);
    }

    private List<String> loadBadWords(String filePath) {
        try {
            return Files.lines(Paths.get(filePath))
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load badwords.txt from " + filePath, e);
        }
    }

    @Override
    public PostDto createPost(PostRequestDto requestDto) {
        for (String word : bannedWords) {
            if (requestDto.getTitle().toLowerCase().contains(word) ||
                    requestDto.getContent().toLowerCase().contains(word)) {
                throw new RuntimeException("Post contains prohibited word: " + word);
            }
        }
        return delegate.createPost(requestDto);
    }
}
