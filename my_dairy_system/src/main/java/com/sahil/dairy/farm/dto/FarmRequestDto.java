package com.sahil.dairy.farm.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmRequestDto {

    private String farmName;

    private String ownerName;

    private String phone;

    private String email;

    private String address;

    private BigDecimal totalLandAcres;
}