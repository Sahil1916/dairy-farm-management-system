package com.sahil.dairy.milk_rate.dto;

import com.sahil.dairy.animal.enums.AnimalType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MilkRateResponseDto {

    private Long id;

    private AnimalType animalType;

    private BigDecimal ratePerLiter;

    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}