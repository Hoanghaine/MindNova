package com.mindnova.dto;
import lombok.*;

import java.time.DayOfWeek;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DoctorScheduleResponseDto {
    private Long id;
    private Integer doctorId;
    private DayOfWeek dayOfWeek;
    private Instant startTime;
    private Instant endTime;
    private boolean isAvailable;
}
