package com.mindnova.dto;

import com.mindnova.common.RoleEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class ProfileDto {
    private Integer id;
    private Integer userId;
    private List<RoleEnum> roles;
    private String avatar;
    private String username;

    private String firstName;
    private String lastName;

    private String phone;
    private String address;
    private LocalDate dob;
    private String gender;
    private String bio;

    private Integer yearOfExperience;

    private Set<String> specialties;
    private Set<CertificateDto> certificates;
}