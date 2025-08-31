package com.mindnova.repositories;

import com.mindnova.dto.DoctorScheduleResponseDto;
import com.mindnova.entities.DoctorSchedule;
import com.mindnova.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    List<DoctorSchedule> findByDoctorIdAndIsAvailableTrue(Integer doctorId);
    List<DoctorSchedule> findDoctorScheduleByDoctor_Id(Integer doctorId);
    Optional<DoctorSchedule> findByDoctorIdAndDayOfWeek(Integer doctorId, DayOfWeek dayOfWeek);
}
