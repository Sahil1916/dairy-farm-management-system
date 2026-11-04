package com.sahil.dairy.user.repository;

import com.sahil.dairy.user.entity.User;
import com.sahil.dairy.user.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    List<User> findByRole(Roles role);

    List<User> findByActive(Boolean active);

    List<User> findByFarmId(Long farmId);

    Optional<User> findByEmailAndActiveTrue(String email);

    boolean existsByRole(Roles role);
    // दिलेल्या Farm ID आणि OWNER Role नुसार Owner शोधते.
    Optional<User> findByFarmIdAndRole(Long farmId, Roles role);

}