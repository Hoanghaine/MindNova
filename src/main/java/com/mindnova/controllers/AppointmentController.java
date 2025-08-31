package com.mindnova.controllers;

import com.mindnova.dto.request.AppointmentFilterDto;
import com.mindnova.dto.request.CreateAppointmentGuestRequest;
import com.mindnova.dto.request.CreateAppointmentRequest;
import com.mindnova.dto.request.UpdateStatusAppointmentRequest;
import com.mindnova.dto.response.AppointmentResponseDto;
import com.mindnova.entities.Appointment;
import com.mindnova.repositories.AppointmentRepository;
import com.mindnova.security.jwt.authentication.JwtAuthenticationToken;
import com.mindnova.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;

    @GetMapping("/search")
    public Page<AppointmentResponseDto> searchAppointments(AppointmentFilterDto filter, Pageable pageable) {
        return appointmentService.searchAppointments(filter, pageable);
    }

    @PostMapping
    public AppointmentResponseDto createAppointment(
            Authentication authentication,
            @RequestBody CreateAppointmentRequest createAppointmentRequest
    ) {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
        Integer patientId = jwtToken.getUserId();
        createAppointmentRequest.setPatientId(patientId);
        return appointmentService.createAppointment(createAppointmentRequest);
    }

    @PatchMapping
    public AppointmentResponseDto updateStatusAppointment(
            Authentication authentication,
            @RequestBody UpdateStatusAppointmentRequest updateStatusAppointmentRequest
    ) {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
        Integer doctorId = jwtToken.getUserId();
        Appointment appointment = appointmentRepository.getReferenceById(updateStatusAppointmentRequest.getId());
//        if (appointment.getDoctor().getId() != doctorId) {
//            throw new RuntimeException("You not allow to change this appointment status!");
//        }
        return appointmentService.updateStatusAppointment(updateStatusAppointmentRequest);
    }

    @PostMapping("/guest")
    public AppointmentResponseDto createAppointmentForGuest(
            Authentication authentication,
            @RequestBody CreateAppointmentGuestRequest createAppointmentGuestRequest
    ) {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
        Integer doctorId = jwtToken.getUserId();

        return appointmentService.createAppointmentForGuest(doctorId, createAppointmentGuestRequest);
    }

    @GetMapping("/doctor/me")
    public Page<AppointmentResponseDto> getAppointmentsForDoctor(Authentication authentication,Pageable pageable) {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
        Integer doctorId = jwtToken.getUserId();
        return appointmentService.getAppointmentsByDoctorId(doctorId,pageable);
    }

    @GetMapping("/patient/me")
    public Page<AppointmentResponseDto> getAppointmentsForPatient(Authentication authentication ,Pageable pageable) {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
        Integer patientId = jwtToken.getUserId();
        return appointmentService.getAppointmentsByPatientId(patientId,pageable);
    }


}
