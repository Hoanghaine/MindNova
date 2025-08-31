package com.mindnova.services.impl;

import com.mindnova.dto.CertificateDto;
import com.mindnova.dto.ProfileDto;
import com.mindnova.entities.Profile;
import com.mindnova.entities.Role;
import com.mindnova.entities.Specialty;
import com.mindnova.repositories.ProfileRepository;
import com.mindnova.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public ProfileDto getMyProfile(Integer userId) {
        System.out.printf("getMyProfile(%d)\n", userId);
        Profile profile = profileRepository.findByUserIdWithDetails(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return ProfileDto.builder()
                .id(profile.getId())
                .userId(profile.getUser().getId())
                .roles(profile.getUser().getRoles().stream().map(
                        Role::getName
                ).toList())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phone(profile.getPhone())
                .address(profile.getAddress())
                .dob(profile.getDob())
                .gender(profile.getGender())
                .bio(profile.getBio())
                .yearOfExperience(profile.getYearOfExperience())
                .specialties(profile.getSpecialties() != null
                        ? profile.getSpecialties().stream()
                        .map(Specialty::getName)
                        .collect(Collectors.toSet())
                        : Collections.emptySet())
                .certificates(profile.getCertificates() != null
                        ? profile.getCertificates().stream()
                        .map(c -> new CertificateDto(
                                c.getTitle(),
                                c.getFileUrl(),
                                c.getIssuedDate(),
                                c.getExpiredAt()
                        ))
                        .collect(Collectors.toSet())
                        : Collections.emptySet())

                .build();
    }
}
