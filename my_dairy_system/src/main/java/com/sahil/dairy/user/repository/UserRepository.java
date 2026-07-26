//package com.sahil.dairy.user.repository;
//
//
//
//
//import com.sahil.dairy.user.entity.User;
//import com.sahil.dairy.user.enums.Roles;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//
//    Optional<User> findByUsername(String username);
//
//    boolean existsByUsername(String username);
//
//    List<User> findByFarmId(Long farmId);
//
//    List<User> findByFarmIdAndRole(Long farmId, Roles role);
//
//    List<User> findByFarmIdAndIsActiveTrue(Long farmId);
//}
