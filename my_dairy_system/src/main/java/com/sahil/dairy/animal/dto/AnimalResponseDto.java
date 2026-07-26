package com.sahil.dairy.animal.dto;

import com.sahil.dairy.animal.enums.AnimalStatus;
import com.sahil.dairy.animal.enums.AnimalType;
import com.sahil.dairy.animal.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalResponseDto {

    private Long id;

    private Long farmId;

    private String farmName;

    private String earTagNumber;

    private String name;

    private AnimalType animalType;

    private String breed;

    private Gender gender;

    private LocalDate dateOfBirth;

    private BigDecimal weightKg;

    private AnimalStatus animalStatus;

    private LocalDate purchaseDate;

    private BigDecimal purchasePrice;

    private LocalDate sellingDate;

    private BigDecimal sellingPrice;

    private LocalDate lastCalvingDate;

    private LocalDate expectedCalvingDate;

    private Integer daysInMilk;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}