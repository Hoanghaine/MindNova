package com.mindnova.controllers;

import com.mindnova.dto.DoctorDto;
import com.mindnova.dto.ProfileDto;
import com.mindnova.dto.response.ApiResponse;
import com.mindnova.entities.User;
import com.mindnova.mappers.DoctorMapper;
import com.mindnova.services.DoctorService;
import com.mindnova.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final ProfileService profileService;

    @GetMapping("/search")
    public Page<DoctorDto> searchDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Long minAppointmentCount,
            @RequestParam(required = false) Long maxAppointmentCount,
            @RequestParam(required = false) String specialtyName,
            Pageable pageable
    ) {
        Page<User> doctors = doctorService.searchDoctors(
                name, gender, minExperience, maxExperience, specialtyName,minAppointmentCount,maxAppointmentCount, pageable
        );
        return doctors.map(DoctorMapper::toDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileDto>> getDetailDoctor(@PathVariable Integer id) {
        ProfileDto profileDto = profileService.getMyProfile(id);

        ApiResponse<ProfileDto> response = new ApiResponse<>(
                true,
                profileDto,
                "Fetched profile successfully",
                null
        );

        return ResponseEntity.ok(response);
    }
}