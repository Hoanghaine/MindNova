package com.mindnova.mappers;

import com.mindnova.dto.response.AppointmentResponseDto;
import com.mindnova.entities.Appointment;
import com.mindnova.entities.Profile;
import com.mindnova.entities.User;

public class AppointmentMapper {

    public static AppointmentResponseDto toDto(Appointment appointment) {
        if (appointment == null) return null;

        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setId(appointment.getId());
        dto.setStartAt(appointment.getStartAt());
        dto.setEndAt(appointment.getEndAt());
        dto.setStatus(appointment.getStatus());
        dto.setIsGuest(appointment.getIsGuest());

        // doctor
        User doctor = appointment.getDoctor();
        if (doctor != null) {
            dto.setDoctorId(doctor.getId());
            Profile profile = doctor.getProfile();
            dto.setDoctorName(profile != null
                    ? profile.getFirstName() + " " + profile.getLastName()
                    : doctor.getUsername());
        }

        // patient
        User patient = appointment.getPatient();
        if (patient != null) {
            dto.setPatientId(patient.getId());
            Profile profile = patient.getProfile();
            dto.setPatientName(profile != null
                    ? profile.getFirstName() + " " + profile.getLastName()
                    : patient.getUsername());
        }

        // guest
        dto.setGuestName(appointment.getGuestName());
        dto.setGuestPhone(appointment.getGuestPhone());
        dto.setGuestAddress(appointment.getGuestAddress());

        return dto;
    }
}
