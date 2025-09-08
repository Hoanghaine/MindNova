package com.mindnova.dto.request;

import com.mindnova.common.PostTagEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private PostTagEnum type;

    private Integer authorId; // ID của người viết bài
}