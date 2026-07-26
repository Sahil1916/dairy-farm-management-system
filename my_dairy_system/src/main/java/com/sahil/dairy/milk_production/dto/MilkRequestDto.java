package com.sahil.dairy.milk_production.dto;

import com.sahil.dairy.milk_production.enums.MilkSession;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MilkRequestDto {

    @NotNull
    private Long animalId;

    @NotNull
    private LocalDate milkDate;

    @NotNull
    private MilkSession milkSession;

    @NotNull
    @Positive
    private BigDecimal quantityLiters;

    @PositiveOrZero
    private BigDecimal fatPercentage;

    @PositiveOrZero
    private BigDecimal snfPercentage;

    @Size(max = 500)
    private String notes;
}