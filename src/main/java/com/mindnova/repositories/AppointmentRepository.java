package com.mindnova.repositories;

import com.mindnova.entities.Appointment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findByDoctorId(Integer doctorId, Pageable pageable);
    Page<Appointment> findByPatientId(Integer patientId, Pageable pageable);
}
