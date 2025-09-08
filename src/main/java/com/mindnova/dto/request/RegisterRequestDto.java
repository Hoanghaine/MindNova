package com.mindnova.dto.request;
import lombok.Data;

@Data
public class RegisterRequestDto {
    String username;
    String email;
    String password;
}
