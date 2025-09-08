package com.mindnova.services;

import com.mindnova.entities.User;

public interface DoctorApprovalService {
    User approveDoctor(Integer doctorId);
}
