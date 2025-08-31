package com.mindnova.dto.request;

import com.mindnova.common.AppointmentStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AppointmentFilterDto {
    private AppointmentStatusEnum status;
    private Boolean isGuest;
    private String guestName;
    private String guestPhone;
    private Integer doctorId;
    private Integer patientId;
}