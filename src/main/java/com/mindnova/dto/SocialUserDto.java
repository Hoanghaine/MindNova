package com.mindnova.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocialUserDto {
    private String id;
    private String email;
    private String name;
    private String avatarUrl;
    private String provider;
}
