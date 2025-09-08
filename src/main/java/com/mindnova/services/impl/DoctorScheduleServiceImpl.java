package com.mindnova.services.impl;

import com.mindnova.dto.DoctorScheduleResponseDto;
import com.mindnova.entities.DoctorSchedule;
import com.mindnova.repositories.DoctorScheduleRepository;
import com.mindnova.services.DoctorScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;

    @Override
    public List<DoctorScheduleResponseDto> getAvailableSchedulesByDoctorId(Integer doctorId) {
        List<DoctorSchedule> schedules = doctorScheduleRepository.findByDoctorIdAndIsAvailableTrue(doctorId);
        return schedules.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorScheduleResponseDto> getSchedulesByDoctorId(Integer doctorId) {
        List<DoctorSchedule> schedules = doctorScheduleRepository.findDoctorScheduleByDoctor_Id(doctorId);
        return schedules.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorScheduleResponseDto updateSchedule(Integer doctorId, DayOfWeek dayOfWeek, boolean isAvailable, LocalTime startTime, LocalTime endTime) {
        DoctorSchedule schedule = doctorScheduleRepository
                .findByDoctorIdAndDayOfWeek(doctorId, dayOfWeek)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        schedule.setAvailable(isAvailable);

        ZoneId zone = ZoneId.systemDefault();
        if (startTime != null) schedule.setStartTime(startTime.atDate(java.time.LocalDate.now()).atZone(zone).toInstant());
        if (endTime != null) schedule.setEndTime(endTime.atDate(java.time.LocalDate.now()).atZone(zone).toInstant());

        return toDto(doctorScheduleRepository.save(schedule));
    }

    private DoctorScheduleResponseDto toDto(DoctorSchedule schedule) {
        return DoctorScheduleResponseDto.builder()
                .id(schedule.getId())
                .doctorId(schedule.getDoctor().getId())
                .dayOfWeek(schedule.getDayOfWeek())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .isAvailable(schedule.isAvailable())
                .build();
    }
}
