package com.mindnova.repositories;

import com.mindnova.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    @Query("SELECT p FROM Profile p " +
            "LEFT JOIN FETCH p.specialties " +
            "LEFT JOIN FETCH p.certificates " +
            "WHERE p.user.id = :userId")
    Optional<Profile> findByUserIdWithDetails(@Param("userId") Integer userId);
}
