package com.mindnova.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class UpdateScheduleRequest {
    private Boolean isAvailable;
    private LocalTime startTime;
    private LocalTime endTime;
}