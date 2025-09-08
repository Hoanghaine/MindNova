package com.mindnova.specifications;

import com.mindnova.common.DoctorStatusEnum;
import com.mindnova.common.RoleEnum;
import com.mindnova.entities.Appointment;
import com.mindnova.entities.User;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class DoctorSpecification {
    public static Specification<User> hasRoleAndStatus(RoleEnum role, DoctorStatusEnum status) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.join("roles").get("name"), role),
                cb.equal(root.get("doctorStatus"), status)
        );
    }
    public static Specification<User> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null) return null;
            return cb.or(
                    cb.like(cb.lower(root.get("profile").get("firstName")), "%" + name.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("profile").get("lastName")), "%" + name.toLowerCase() + "%")
            );
        };
    }

    public static Specification<User> hasGender(String gender) {
        return (root, query, cb) ->
                gender == null ? null : cb.equal(root.get("profile").get("gender"), gender);
    }

    public static Specification<User> minExperience(Integer min) {
        return (root, query, cb) ->
                min == null ? null : cb.greaterThanOrEqualTo(root.get("profile").get("yearOfExperience"), min);
    }

    public static Specification<User> maxExperience(Integer maxExperience) {
        return (root, query, cb) ->
                maxExperience == null ? null :
                        cb.lessThanOrEqualTo(root.get("profile").get("yearOfExperience"), maxExperience);
    }

    public static Specification<User> hasSpecialty(String specialtyName) {
        return (root, query, cb) -> {
            if (specialtyName == null) return null;
            Join<Object, Object> join = root.join("profile").join("specialties");
            return cb.equal(join.get("name"), specialtyName);
        };
    }

    public static Specification<User> appointmentCountBetween(Long min, Long max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            Subquery<Long> sub = query.subquery(Long.class);
            Root<Appointment> app = sub.from(Appointment.class);
            sub.select(cb.count(app))
                    .where(cb.equal(app.get("doctor"), root));

            Predicate predicate = cb.conjunction();
            if (min != null) predicate = cb.and(predicate, cb.ge(sub, min));
            if (max != null) predicate = cb.and(predicate, cb.le(sub, max));
            return predicate;
        };
    }


}
