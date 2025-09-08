package com.mindnova.repositories;

import com.mindnova.common.RoleEnum;
import com.mindnova.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}