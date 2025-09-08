package com.mindnova.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mindnova.common.AuthProviderEnum;
import com.mindnova.common.DoctorStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "[user]")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( name = "username",nullable = false,length = 100, unique = true)
    private String username;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(length = 255)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "[user_roles]", // bảng trung gian
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Enumerated(EnumType.STRING)
    private DoctorStatusEnum doctorStatus = DoctorStatusEnum.PENDING;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Profile profile;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthProviderEnum provider;

    @Column(name = "created_at",nullable = false, columnDefinition = "DATETIME2 DEFAULT SYSDATETIME()")
    private LocalDateTime createdAt = LocalDateTime.now();
}