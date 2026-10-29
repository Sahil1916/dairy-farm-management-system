package com.sahil.dairy.animal.dto;

import com.sahil.dairy.animal.enums.AnimalStatus;
import com.sahil.dairy.animal.enums.AnimalType;
import com.sahil.dairy.animal.enums.Gender;
import com.sahil.dairy.animal.enums.LactationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalRequestDto {

    @NotNull(message = "Farm Id is required")
    private Long farmId;

    @NotBlank(message = "Ear tag number is required")
    private String earTagNumber;

    @NotBlank(message = "Animal name is required")
    private String name;

    @NotNull(message = "Animal type is required")
    private AnimalType animalType;

    @NotBlank(message = "Breed is required")
    private String breed;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @Positive(message = "Weight must be greater than 0")
    private BigDecimal weightKg;

    @NotNull(message = "Animal status is required")
    private AnimalStatus animalStatus;

    private LactationStatus lactationStatus;

    private LocalDate purchaseDate;

    @Positive(message = "Purchase price must be greater than 0")
    private BigDecimal purchasePrice;

    private LocalDate sellingDate;

    @Positive(message = "Selling price must be greater than 0")
    private BigDecimal sellingPrice;

    private LocalDate lastCalvingDate;

    private LocalDate expectedCalvingDate;
}