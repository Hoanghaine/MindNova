package com.mindnova.mappers;


import com.mindnova.dto.DoctorDto;
import com.mindnova.entities.Profile;
import com.mindnova.entities.User;

import java.util.stream.Collectors;

public class DoctorMapper {

    public static DoctorDto toDto(User user) {
        if (user == null) return null;

        DoctorDto dto = new DoctorDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        Profile profile = user.getProfile();
        if (profile != null) {
            dto.setFirstName(profile.getFirstName());
            dto.setLastName(profile.getLastName());
            dto.setGender(profile.getGender());
            dto.setYearOfExperience(profile.getYearOfExperience());

            if (profile.getSpecialties() != null) {
                dto.setSpecialties(profile.getSpecialties().stream().map( specialty -> specialty.getName()).collect(Collectors.toSet()));
            }
        }

        return dto;
    }
}
