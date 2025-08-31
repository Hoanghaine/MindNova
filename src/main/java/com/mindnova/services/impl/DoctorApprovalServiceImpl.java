package com.mindnova.services.impl;

import com.mindnova.common.DoctorStatusEnum;
import com.mindnova.common.RoleEnum;
import com.mindnova.entities.DoctorSchedule;
import com.mindnova.entities.User;
import com.mindnova.repositories.DoctorScheduleRepository;
import com.mindnova.repositories.UserRepository;
import com.mindnova.services.DoctorApprovalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class DoctorApprovalServiceImpl implements DoctorApprovalService {

    private final UserRepository userRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;

    @Transactional
    public User approveDoctor(Integer doctorId) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (!doctor.getRoles().contains(RoleEnum.DOCTOR)) {
            throw new RuntimeException("User is not a doctor");
        }

        doctor.setDoctorStatus(DoctorStatusEnum.APPROVED);
        userRepository.save(doctor);

        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            DoctorSchedule schedule = new DoctorSchedule();
            schedule.setDoctor(doctor);
            schedule.setDayOfWeek(dayOfWeek);
            schedule.setAvailable(false);

            schedule.setStartTime(LocalTime.of(9,0).atDate(LocalDate.now())
                    .atZone(ZoneOffset.UTC).toInstant());
            schedule.setEndTime(LocalTime.of(17,0).atDate(LocalDate.now())
                    .atZone(ZoneOffset.UTC).toInstant());

            doctorScheduleRepository.save(schedule);
        }
        return doctor;
    }
}