package com.mindnova.services;

import com.mindnova.dto.request.AppointmentFilterDto;
import com.mindnova.dto.request.CreateAppointmentGuestRequest;
import com.mindnova.dto.request.CreateAppointmentRequest;
import com.mindnova.dto.request.UpdateStatusAppointmentRequest;
import com.mindnova.dto.response.AppointmentResponseDto;
import com.mindnova.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface AppointmentService {
    AppointmentResponseDto createAppointment(CreateAppointmentRequest createAppointmentRequest);
    AppointmentResponseDto createAppointmentForGuest(Integer doctorId, CreateAppointmentGuestRequest createAppointmentGuestRequest);
    Page<AppointmentResponseDto> getAppointmentsByDoctorId(Integer doctorId,Pageable pageable);
    Page<AppointmentResponseDto> getAppointmentsByPatientId(Integer patientId,Pageable pageable);
    Page<AppointmentResponseDto> searchAppointments(AppointmentFilterDto filter, Pageable pageable);
    AppointmentResponseDto updateStatusAppointment(UpdateStatusAppointmentRequest updateStatusAppointmentRequest);
}
