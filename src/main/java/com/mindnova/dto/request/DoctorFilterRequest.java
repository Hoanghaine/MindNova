package com.mindnova.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorFilterRequest {
    private String name;
    private String gender;
    private Integer minExperience;
    private Integer maxExperience;
    private String specialtyName;
}