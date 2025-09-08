package com.mindnova.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CreateAppointmentRequest {
    private Integer patientId;
    private Integer doctorId;
    private Instant startAt;
    private Instant endAt;
}