package com.mindnova.entities;

import com.mindnova.common.AuthProviderEnum;
import com.mindnova.services.AuthService;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "[profile]")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(columnDefinition = "VARCHAR(200)")
    private String avatar;

    @Column(name = "first_name",columnDefinition = "NVARCHAR(50)")
    private String firstName;

    @Column(name = "last_name",columnDefinition = "NVARCHAR(50)")
    private String lastName;

    private LocalDate dob;
    private String gender;

    @Column(unique = true)
    @Pattern(regexp = "^(0|\\+84)\\d{9,10}$")
    private String phone;

    private String address;
    private String bio;

    @Column(name = "year_of_experience")
    private Integer yearOfExperience;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_specialty",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private Set<Specialty> specialties = new HashSet<>();



    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Certificate> certificates = new HashSet<>();
}
