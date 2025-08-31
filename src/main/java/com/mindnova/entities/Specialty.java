
package com.mindnova.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "specialty")
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;
}
