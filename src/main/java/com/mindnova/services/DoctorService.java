package com.mindnova.services;

import com.mindnova.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DoctorService {
    public Page<User> searchDoctors(
            String name,
            String gender,
            Integer minExperience,
            Integer maxExperience,
            String specialty,
            Long minAppointmentCount,
            Long maxAppointmentCount,
            Pageable pageable);
    Optional<User> getReferenceById(Integer id);
}
