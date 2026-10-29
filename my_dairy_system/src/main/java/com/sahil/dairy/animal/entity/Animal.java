package com.sahil.dairy.animal.entity;

import com.sahil.dairy.animal.enums.AnimalStatus;
import com.sahil.dairy.animal.enums.AnimalType;
import com.sahil.dairy.animal.enums.Gender;
import com.sahil.dairy.animal.enums.LactationStatus;
import com.sahil.dairy.farm.entity.Farm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "animals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @Column(name = "ear_tag_number", nullable = false, unique = true)
    @UniqueElements
    private String earTagNumber;



    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_type")
    private AnimalType animalType;

    @Column(nullable = false)
    private String breed;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "weight_kg", precision = 8, scale = 2)
    private BigDecimal weightKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_status")
    private AnimalStatus animalStatus;


    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "purchase_price", precision = 12, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "selling_date")
    private LocalDate sellingDate;

    @Column(name = "selling_price", precision = 12, scale = 2)
    private BigDecimal sellingPrice;



    @Column(name = "last_calving_date")
    private LocalDate lastCalvingDate;

    @Column(name = "expected_calving_date")
    private LocalDate expectedCalvingDate;

    private LactationStatus lactationStatus;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

