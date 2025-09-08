package com.mindnova.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;

@Entity
@Table(
        name = "doctor_schedule",
        uniqueConstraints = @UniqueConstraint(columnNames = {"doctor_id", "day_of_week"})
)

@Getter
@Setter
public class DoctorSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable = false;
}
