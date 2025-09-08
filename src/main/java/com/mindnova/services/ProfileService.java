package com.mindnova.services;

import com.mindnova.dto.ProfileDto;
import com.mindnova.entities.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileService {
    ProfileDto getMyProfile(Integer userId);

}
