package com.mindnova.common;

import java.time.*;

public enum FixedSlotEnum {
    SLOT_9AM(LocalTime.of(9, 0)),
    SLOT_10AM(LocalTime.of(10, 0)),
    SLOT_11AM(LocalTime.of(11, 0)),
    SLOT_1PM(LocalTime.of(13, 0)),
    SLOT_2PM(LocalTime.of(14, 0)),
    SLOT_3PM(LocalTime.of(15, 0)),
    SLOT_4PM(LocalTime.of(16, 0)),
    SLOT_7PM(LocalTime.of(19, 0)),
    SLOT_8PM(LocalTime.of(20, 0));

    private final LocalTime localTime;

    FixedSlotEnum(LocalTime localTime) {
        this.localTime = localTime;
    }

    public Instant toInstant(LocalDate date, ZoneId zone) {
        return ZonedDateTime.of(date, localTime, zone).toInstant();
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
