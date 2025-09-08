package com.mindnova.controllers;

import com.mindnova.dto.DoctorScheduleResponseDto;
import com.mindnova.dto.request.UpdateScheduleRequest;
import com.mindnova.services.DoctorScheduleService;
import com.mindnova.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/doctor-schedules")
@RequiredArgsConstructor
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;
    private final JwtUtils jwtUtils;
    @GetMapping("/me")
    public List<DoctorScheduleResponseDto> getAvailableSchedulesForDoctor(@RequestHeader("Authorization") String authHeader) {
        Integer doctorId = jwtUtils.extractAccountIdFromAuthHeader(authHeader);
        return doctorScheduleService.getSchedulesByDoctorId( doctorId);
    }

    @GetMapping("/{doctorId}")
    public List<DoctorScheduleResponseDto> getAvailableSchedulesForUser(@PathVariable Integer doctorId) {
        return doctorScheduleService.getAvailableSchedulesByDoctorId(doctorId);
    }

    @PatchMapping("/{dayOfWeek}")
    public DoctorScheduleResponseDto updateSchedule(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable DayOfWeek dayOfWeek,
            @RequestBody UpdateScheduleRequest updateScheduleRequest
    ) {
        Integer doctorId = jwtUtils.extractAccountIdFromAuthHeader(authHeader);
        return doctorScheduleService.updateSchedule(doctorId, dayOfWeek,updateScheduleRequest.getIsAvailable(), updateScheduleRequest.getStartTime(), updateScheduleRequest.getEndTime());
    }
}
