package com.mindnova.adapter;

import com.mindnova.dto.SocialUserDto;
import com.mindnova.entities.User;

public interface SocialLoginAdapter {
    SocialUserDto authenticate(String accessToken);
    String getProviderName();
}
