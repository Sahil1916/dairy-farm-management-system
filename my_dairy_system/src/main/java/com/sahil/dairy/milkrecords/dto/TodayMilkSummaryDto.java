package com.sahil.dairy.milkrecords.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodayMilkSummaryDto {
    BigDecimal cowMilk;
    BigDecimal buffaloMilk;
    BigDecimal totalMilk;


}
