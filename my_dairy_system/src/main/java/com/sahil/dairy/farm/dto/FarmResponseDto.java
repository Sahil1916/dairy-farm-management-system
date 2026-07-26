package com.sahil.dairy.farm.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmResponseDto {

    private Long id;

    private String farmName;

    private String ownerName;

    private String phone;

    private String email;

    private String address;

    private BigDecimal totalLandAcres;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}