package com.mindnova.dto;

import com.mindnova.common.PostTagEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private PostTagEnum type;
    private LocalDateTime createdAt;
    private AuthorDto author;
    private int commentCount;
    private int likeCount;
}
