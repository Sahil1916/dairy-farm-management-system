package com.sahil.dairy.milk_rate.dto;

import com.sahil.dairy.animal.enums.AnimalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MilkRateRequestDto {

    @NotNull(message = "Animal type is required")
    private AnimalType animalType;

    @NotNull(message = "Rate per liter is required")
    @DecimalMin(value = "0.01", message = "Rate must be greater than zero")
    private BigDecimal ratePerLiter;

    @NotNull(message = "Effective from date is required")
    private LocalDate effectiveFrom;
}