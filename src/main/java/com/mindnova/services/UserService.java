package com.mindnova.services;

import com.mindnova.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface UserService {
    Page<UserDto> getUsers(Pageable pageable);

}
