package com.mindnova.services.impl;

import com.mindnova.common.DoctorStatusEnum;
import com.mindnova.common.RoleEnum;
import com.mindnova.entities.User;
import com.mindnova.repositories.UserRepository;
import com.mindnova.services.DoctorService;
import com.mindnova.specifications.DoctorSpecification;
import com.mindnova.utils.PageableSearchDoctor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final UserRepository userRepository;

    @Override
    public Page<User> searchDoctors(
            String name,
            String gender,
            Integer minExperience,
            Integer maxExperience,
            String specialty,
            Long minAppointmentCount,
            Long maxAppointmentCount,
            Pageable pageable
    ) {
        Specification<User> spec = DoctorSpecification.hasRoleAndStatus(RoleEnum.DOCTOR, DoctorStatusEnum.APPROVED)
                .and(DoctorSpecification.hasName(name))
                .and(DoctorSpecification.hasGender(gender))
                .and(DoctorSpecification.minExperience(minExperience))
                .and(DoctorSpecification.maxExperience(maxExperience))
                .and(DoctorSpecification.hasSpecialty(specialty))
//                .and(DoctorSpecification.hasAppointmentCountLessThan(maxAppointmentCount)
//                        .and(DoctorSpecification.hasAppointmentCountGreaterThan(minAppointmentCount)))
//                ;
                .and(DoctorSpecification.appointmentCountBetween(minAppointmentCount, maxAppointmentCount));
        Pageable mappedPageable = PageableSearchDoctor.mapSort(pageable);
        return userRepository.findAll(spec, mappedPageable);
    }
    @Override
    public Optional<User> getReferenceById(Integer id) {
        return Optional.of(userRepository.getReferenceById(id));
    }
}
