package com.mindnova.dto.request;

import com.mindnova.common.PostTagEnum;
import lombok.Data;

@Data
public class PostFilterDto {
    private String title;
    private PostTagEnum type;
}