package com.sahil.dairy.milk_production.dto;

import com.sahil.dairy.animal.enums.AnimalType;
import com.sahil.dairy.milk_production.enums.MilkSession;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MilkResponseDto {

    private Long id;

    private Long animalId;

    private String animalName;

    private String earTagNumber;

    private AnimalType animalType;

    private LocalDate milkDate;

    private MilkSession milkSession;

    private BigDecimal quantityLiters;

    private BigDecimal fatPercentage;

    private BigDecimal snfPercentage;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
