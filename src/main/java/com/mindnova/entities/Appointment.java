package com.mindnova.entities;

import com.mindnova.common.AppointmentStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "appointment")
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @Pattern(regexp = "^(0|\\+84)\\d{9,10}$")
    @Column(name = "guest_phone")
    private String guestPhone;

    @Column(name = "guest_name")
    private String guestName;

    @Column(name = "guest_address")
    private String guestAddress;

    @Column(name = "is_guest")
    private Boolean isGuest = false;

    @Column(name = "start_at", nullable = false)
    private Instant startAt;

    @Column(name = "end_at", nullable = false)
    private Instant endAt;

    @Enumerated(EnumType.STRING)
    private AppointmentStatusEnum status = AppointmentStatusEnum.PENDING;
}
