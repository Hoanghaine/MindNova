package com.mindnova.controllers;

import com.mindnova.dto.request.AppointmentFilterDto;
import com.mindnova.dto.request.CreateAppointmentGuestRequest;
import com.mindnova.dto.request.CreateAppointmentRequest;
import com.mindnova.dto.request.UpdateStatusAppointmentRequest;
import com.mindnova.dto.response.AppointmentResponseDto;
import com.mindnova.entities.Appointment;
import com.mindnova.repositories.AppointmentRepository;
import com.mindnova.services.AppointmentService;
import com.mindnova.utils.JwtUtils;
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
    private final JwtUtils jwtUtils;

    @GetMapping("/search")
    public Page<AppointmentResponseDto> searchAppointments(AppointmentFilterDto filter, Pageable pageable) {
        return appointmentService.searchAppointments(filter, pageable);
    }

    @PostMapping
    public AppointmentResponseDto createAppointment(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CreateAppointmentRequest createAppointmentRequest
    ) {
        Integer patientId =  jwtUtils.extractAccountIdFromAuthHeader(authHeader);
        createAppointmentRequest.setPatientId(patientId);
        return appointmentService.createAppointment(createAppointmentRequest);
    }

    @PatchMapping
    public AppointmentResponseDto updateStatusAppointment(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody UpdateStatusAppointmentRequest updateStatusAppointmentRequest
    ) {
        Integer doctorId =  jwtUtils.extractAccountIdFromAuthHeader(authHeader);
        Appointment appointment = appointmentRepository.getReferenceById(updateStatusAppointmentRequest.getId());
        if (appointment.getDoctor().getId() != doctorId) {
            throw new RuntimeException("You are not allowed to change this appointment status!");
        }
        return appointmentService.updateStatusAppointment(updateStatusAppointmentRequest);
    }

    @PostMapping("/guest")
    public AppointmentResponseDto createAppointmentForGuest(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CreateAppointmentGuestRequest createAppointmentGuestRequest
    ) {

        Integer doctorId =  jwtUtils.extractAccountIdFromAuthHeader(authHeader);

        return appointmentService.createAppointmentForGuest(doctorId, createAppointmentGuestRequest);
    }

    @GetMapping("/doctor/me")
    public Page<AppointmentResponseDto> getAppointmentsForDoctor(@RequestHeader("Authorization") String authHeader,Pageable pageable) {
        Integer doctorId =  jwtUtils.extractAccountIdFromAuthHeader(authHeader);
        return appointmentService.getAppointmentsByDoctorId(doctorId,pageable);
    }

    @GetMapping("/patient/me")
    public Page<AppointmentResponseDto> getAppointmentsForPatient(@RequestHeader("Authorization") String authHeader,Pageable pageable) {
        Integer patientId = jwtUtils.extractAccountIdFromAuthHeader(authHeader);
        return appointmentService.getAppointmentsByPatientId(patientId,pageable);
    }


}
