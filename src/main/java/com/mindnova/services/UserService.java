package com.mindnova.services;

import com.mindnova.dto.SocialUserDto;
import com.mindnova.dto.UserDto;
import com.mindnova.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface UserService {
    Page<UserDto> getUsers(Pageable pageable);
    User registerSocialUser(SocialUserDto socialUser);
}
