package com.mindnova.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "certificate")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Liên kết với Profile
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(nullable = false)
    private String title;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "issued_date")
    private LocalDate issuedDate;

    @Column(name = "expired_at")
    private LocalDate expiredAt;
}
