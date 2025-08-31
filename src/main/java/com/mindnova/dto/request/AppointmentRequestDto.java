package com.mindnova.dto.request;

import com.mindnova.common.AppointmentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequestDto {
    private Integer doctorId;
    private Integer patientId;
    private String guestPhone;
    private String guestName;
    private String guestAddress;
    private Boolean isGuest;
    private Instant startAt;
    private Instant endAt;
    private AppointmentStatusEnum status;
}
