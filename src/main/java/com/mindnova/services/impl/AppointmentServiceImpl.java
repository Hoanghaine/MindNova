package com.mindnova.services.impl;

import com.mindnova.common.AppointmentStatusEnum;
import com.mindnova.common.NotificationChannelEnum;
import com.mindnova.dto.NotificationEventDto;
import com.mindnova.dto.request.AppointmentFilterDto;
import com.mindnova.dto.request.CreateAppointmentGuestRequest;
import com.mindnova.dto.request.CreateAppointmentRequest;
import com.mindnova.dto.request.UpdateStatusAppointmentRequest;
import com.mindnova.dto.response.AppointmentResponseDto;
import com.mindnova.entities.Appointment;
import com.mindnova.entities.User;
import com.mindnova.mappers.AppointmentMapper;
import com.mindnova.publisher.NotificationPublisher;
import com.mindnova.repositories.AppointmentRepository;
import com.mindnova.repositories.UserRepository;
import com.mindnova.services.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final NotificationPublisher notificationPublisher;

    @Override
    public AppointmentResponseDto createAppointment(CreateAppointmentRequest createAppointmentRequest) {
        User patientUser = userRepository.getReferenceById(createAppointmentRequest.getPatientId());
        User doctorUser = userRepository.getReferenceById(createAppointmentRequest.getDoctorId());

        Appointment appointment = new Appointment();
        appointment.setPatient(patientUser);
        appointment.setDoctor(doctorUser);
        appointment.setStartAt(createAppointmentRequest.getStartAt());
        appointment.setEndAt(createAppointmentRequest.getEndAt());
        appointment.setStatus(AppointmentStatusEnum.PENDING);

        return toDto(appointmentRepository.save(appointment));
    }

    @Override
    @Transactional
    public AppointmentResponseDto createAppointmentForGuest(Integer doctorId, CreateAppointmentGuestRequest createAppointmentGuestRequest) {
        User doctorUser = userRepository.getReferenceById(doctorId);

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctorUser);
        appointment.setGuestName(createAppointmentGuestRequest.getName());
        appointment.setGuestPhone(createAppointmentGuestRequest.getPhone());
        appointment.setGuestAddress(createAppointmentGuestRequest.getAddress());
        appointment.setIsGuest(true);
        appointment.setStartAt(createAppointmentGuestRequest.getStartAt());
        appointment.setStatus(AppointmentStatusEnum.PENDING);

        Appointment appointmentSaved = appointmentRepository.save(appointment);
        return AppointmentMapper.toDto(appointmentSaved);
    }

    @Override
    public Page<AppointmentResponseDto> getAppointmentsByDoctorId(Integer doctorId, Pageable pageable) {
        return appointmentRepository.findByDoctorId(doctorId, pageable)
                .map(AppointmentMapper::toDto);
    }

    @Override
    public Page<AppointmentResponseDto> getAppointmentsByPatientId(Integer patientId, Pageable pageable) {
        return appointmentRepository.findByPatientId(patientId,pageable)
                .map(AppointmentMapper::toDto);
    }

    @Override
    public Page<AppointmentResponseDto> searchAppointments(AppointmentFilterDto filter, Pageable pageable) {
        Appointment probe = new Appointment();
        probe.setStatus(filter.getStatus());
        probe.setIsGuest(filter.getIsGuest());
        probe.setGuestName(filter.getGuestName());
        probe.setGuestPhone(filter.getGuestPhone());

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withMatcher("guestName", m -> m.contains().ignoreCase())
                .withMatcher("guestPhone", m -> m.contains());

        Example<Appointment> example = Example.of(probe, matcher);


        Page<Appointment> appointments = appointmentRepository.findAll(
                example,
                pageable
        );

        return appointments.map(AppointmentMapper::toDto);
    }

    @Override
    @PreAuthorize("hasRole('DOCTOR')")
    public AppointmentResponseDto updateStatusAppointment(UpdateStatusAppointmentRequest updateStatusAppointmentRequest) {
        Appointment appointment = appointmentRepository.findById(updateStatusAppointmentRequest.getId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(updateStatusAppointmentRequest.getStatus());
        Appointment saved = appointmentRepository.save(appointment);

        NotificationEventDto dto = NotificationEventDto.builder()
                .userId(saved.getPatient().getId())
                .userEmail(saved.getPatient().getEmail())
                .title("Appointment Status Updated")
                .content("Your appointment on " + saved.getStartAt() + " has been updated to: " + saved.getStatus())
                .channels(Set.of(
                        NotificationChannelEnum.IN_APP,
                        NotificationChannelEnum.EMAIL,
                        NotificationChannelEnum.WEB_SOCKET
                ))
                .build();

        // Gửi notification qua observer manager
        notificationPublisher.publish(dto);

        return AppointmentMapper.toDto(saved);
    }



    private AppointmentResponseDto toDto(Appointment appointment) {
        if (appointment == null) return null;

        return AppointmentResponseDto.builder()
                .id(appointment.getId())
                .doctorId(appointment.getDoctor() != null ? appointment.getDoctor().getId() : null)
                .doctorName(appointment.getDoctor() != null ? appointment.getDoctor().getProfile().getFirstName() :null)
                .patientId(appointment.getPatient() != null ? appointment.getPatient().getId() : null)
                .patientName(appointment.getPatient() != null ? appointment.getPatient().getProfile().getFirstName() :null)
                .guestPhone(appointment.getGuestPhone())
                .guestName(appointment.getGuestName())
                .guestAddress(appointment.getGuestAddress())
                .isGuest(appointment.getIsGuest())
                .startAt(appointment.getStartAt())
                .endAt(appointment.getEndAt())
                .status(appointment.getStatus())
                .build();
    }

}
