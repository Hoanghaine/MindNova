package com.mindnova.services;

import com.mindnova.dto.DoctorScheduleResponseDto;
import com.mindnova.entities.DoctorSchedule;
import com.mindnova.entities.User;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface DoctorScheduleService {
    List<DoctorScheduleResponseDto> getAvailableSchedulesByDoctorId(Integer doctorId);
    List<DoctorScheduleResponseDto> getSchedulesByDoctorId(Integer doctorId);
    DoctorScheduleResponseDto updateSchedule(Integer doctorId, DayOfWeek dayOfWeek, boolean isAvailable, LocalTime startTime, LocalTime endTime);
}
