package com.mindnova.controllers;

import com.mindnova.dto.DoctorScheduleResponseDto;
import com.mindnova.dto.request.UpdateScheduleRequest;
import com.mindnova.security.jwt.authentication.JwtAuthenticationToken;
import com.mindnova.services.DoctorScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/doctor-schedules")
@RequiredArgsConstructor
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;

    @GetMapping("/me")
    public List<DoctorScheduleResponseDto> getAvailableSchedulesForDoctor(Authentication authentication) {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
        Integer doctorId = jwtToken.getUserId();
        return doctorScheduleService.getSchedulesByDoctorId( doctorId);
    }

    @GetMapping("/{doctorId}")
    public List<DoctorScheduleResponseDto> getAvailableSchedulesForUser(@PathVariable Integer doctorId) {
        return doctorScheduleService.getAvailableSchedulesByDoctorId(doctorId);
    }

    @PatchMapping("/{dayOfWeek}")
    public DoctorScheduleResponseDto updateSchedule(
            Authentication authentication,
            @PathVariable DayOfWeek dayOfWeek,
            @RequestBody UpdateScheduleRequest updateScheduleRequest
    ) {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
        Integer doctorId = jwtToken.getUserId();
        return doctorScheduleService.updateSchedule(doctorId, dayOfWeek,updateScheduleRequest.getIsAvailable(), updateScheduleRequest.getStartTime(), updateScheduleRequest.getEndTime());
    }
}
