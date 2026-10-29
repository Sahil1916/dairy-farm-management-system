package com.sahil.dairy.milk_rate.service;

import com.sahil.dairy.animal.enums.AnimalType;
import com.sahil.dairy.milk_rate.dto.MilkRateRequestDto;
import com.sahil.dairy.milk_rate.dto.MilkRateResponseDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface MilkRateService {

    MilkRateResponseDto createMilkRate(MilkRateRequestDto request);

    MilkRateResponseDto updateMilkRate(Long id, MilkRateRequestDto request);

    MilkRateResponseDto getMilkRateById(Long id);

    List<MilkRateResponseDto> getAllMilkRates();

    ResponseEntity<String> deleteMilkRate(Long id);

    MilkRateResponseDto getCurrentRate(AnimalType animalType);

    List<MilkRateResponseDto> getRateHistory(AnimalType animalType);

    MilkRateResponseDto getApplicableRate(
            AnimalType animalType,
            LocalDate date);
}