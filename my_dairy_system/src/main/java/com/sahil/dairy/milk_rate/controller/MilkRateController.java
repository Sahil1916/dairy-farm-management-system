package com.sahil.dairy.milk_rate.controller;

import com.sahil.dairy.animal.enums.AnimalType;
import com.sahil.dairy.milk_rate.dto.MilkRateRequestDto;
import com.sahil.dairy.milk_rate.dto.MilkRateResponseDto;
import com.sahil.dairy.milk_rate.service.MilkRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/milkrecords-rates")
public class MilkRateController {

    private final MilkRateService milkRateService;

    @PostMapping
    public MilkRateResponseDto createMilkRate(
            @Valid @RequestBody MilkRateRequestDto request) {

        return milkRateService.createMilkRate(request);
    }

    @PutMapping("/{id}")
    public MilkRateResponseDto updateMilkRate(
            @PathVariable Long id,
            @Valid @RequestBody MilkRateRequestDto request) {

        return milkRateService.updateMilkRate(id, request);
    }

    @GetMapping("/{id}")
    public MilkRateResponseDto getMilkRateById(
            @PathVariable Long id) {

        return milkRateService.getMilkRateById(id);
    }

    @GetMapping
    public List<MilkRateResponseDto> getAllMilkRates() {

        return milkRateService.getAllMilkRates();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMilkRate(
            @PathVariable Long id) {

        return milkRateService.deleteMilkRate(id);
    }

    @GetMapping("/current")
    public MilkRateResponseDto getCurrentRate(
            @RequestParam AnimalType animalType) {

        return milkRateService.getCurrentRate(animalType);
    }

    @GetMapping("/history")
    public List<MilkRateResponseDto> getRateHistory(
            @RequestParam AnimalType animalType) {

        return milkRateService.getRateHistory(animalType);
    }

    @GetMapping("/applicable")
    public MilkRateResponseDto getApplicableRate(
            @RequestParam AnimalType animalType,
            @RequestParam LocalDate date) {

        return milkRateService.getApplicableRate(animalType, date);
    }
}