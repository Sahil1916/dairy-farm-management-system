//package com.sahil.dairy.user.entity;
//
//
//
//import com.sahil.dairy.farm.entity.Farm;
//import com.sahil.dairy.user.enums.Roles;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "users")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "farm_id", nullable = false)
//    private Farm farm;
//
//    @Column(name = "username", unique = true, nullable = false, length = 50)
//    private String username;
//
//    @Column(name = "password_hash", nullable = false, length = 255)
//    private String passwordHash;
//
//    @Column(name = "full_name", nullable = false, length = 100)
//    private String fullName;
//
//    @Column(name = "phone", length = 20)
//    private String phone;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "role", nullable = false)
//    private Roles role;
//
//    @Column(name = "is_active")
//    private Boolean isActive = true;
//
//    @CreationTimestamp
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//
//
//}
