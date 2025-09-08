package com.mindnova.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeResponseDto {
    private Integer id;
    private String email;
    private String username;
    private List<String> authorities;
}
