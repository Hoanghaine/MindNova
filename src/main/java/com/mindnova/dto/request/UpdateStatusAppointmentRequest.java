package com.mindnova.dto.request;

import com.mindnova.common.AppointmentStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UpdateStatusAppointmentRequest {
    private Long id;
    private AppointmentStatusEnum status;
}