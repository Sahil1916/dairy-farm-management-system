package com.sahil.dairy.farm.services;

import com.sahil.dairy.farm.dto.FarmRequestDto;
import com.sahil.dairy.farm.dto.FarmResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FarmService {

    FarmResponseDto createFarm(FarmRequestDto request);

    FarmResponseDto getFarmById(Long id);

    List<FarmResponseDto> getAllFarms();

    FarmResponseDto updateFarm(Long id , FarmRequestDto request);

    ResponseEntity<String> deleteFarm(Long id);
}
