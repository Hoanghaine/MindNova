package com.mindnova.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDto {
    private String avatar;
    private String username;
}
