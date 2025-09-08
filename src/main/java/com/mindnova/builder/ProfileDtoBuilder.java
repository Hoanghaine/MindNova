package com.mindnova.builder;

import com.mindnova.dto.CertificateDto;
import com.mindnova.dto.ProfileDto;
import com.mindnova.common.RoleEnum;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProfileDtoBuilder {

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

    private Set<String> specialties = new HashSet<>();
    private Set<CertificateDto> certificates = new HashSet<>();

    private ProfileDtoBuilder() {
    }

    public static ProfileDtoBuilder builder() {
        return new ProfileDtoBuilder();
    }

    public ProfileDtoBuilder id(Integer id) {
        this.id = id;
        return this;
    }

    public ProfileDtoBuilder userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public ProfileDtoBuilder roles(List<RoleEnum> roles) {
        this.roles = roles;
        return this;
    }

    public ProfileDtoBuilder avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public ProfileDtoBuilder username(String username) {
        this.username = username;
        return this;
    }

    public ProfileDtoBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ProfileDtoBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ProfileDtoBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public ProfileDtoBuilder address(String address) {
        this.address = address;
        return this;
    }

    public ProfileDtoBuilder dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public ProfileDtoBuilder gender(String gender) {
        this.gender = gender;
        return this;
    }

    public ProfileDtoBuilder bio(String bio) {
        this.bio = bio;
        return this;
    }

    public ProfileDtoBuilder yearOfExperience(Integer yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
        return this;
    }

    public ProfileDtoBuilder specialties(Set<String> specialties) {
        this.specialties = specialties != null ? specialties : new HashSet<>();
        return this;
    }

    public ProfileDtoBuilder certificates(Set<CertificateDto> certificates) {
        this.certificates = certificates != null ? certificates : new HashSet<>();
        return this;
    }

    public ProfileDto build() {
        ProfileDto dto = new ProfileDto();
        dto.setId(this.id);
        dto.setUserId(this.userId);
        dto.setRoles(this.roles != null ? this.roles : Collections.emptyList());
        dto.setAvatar(this.avatar);
        dto.setUsername(this.username);
        dto.setFirstName(this.firstName);
        dto.setLastName(this.lastName);
        dto.setPhone(this.phone);
        dto.setAddress(this.address);
        dto.setDob(this.dob);
        dto.setGender(this.gender);
        dto.setBio(this.bio);
        dto.setYearOfExperience(this.yearOfExperience);
        dto.setSpecialties(this.specialties);
        dto.setCertificates(this.certificates);
        return dto;
    }
}
