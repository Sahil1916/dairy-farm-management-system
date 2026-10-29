package com.sahil.dairy.milk_rate.service;

import com.sahil.dairy.animal.enums.AnimalType;
import com.sahil.dairy.exception.BadRequestException;
import com.sahil.dairy.exception.ResourceNotFoundException;
import com.sahil.dairy.milk_rate.dto.MilkRateRequestDto;
import com.sahil.dairy.milk_rate.dto.MilkRateResponseDto;
import com.sahil.dairy.milk_rate.entity.MilkRate;
import com.sahil.dairy.milk_rate.repository.MilkRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MilkRateServiceImpl implements MilkRateService {

    private final MilkRateRepository milkRateRepository;

    private MilkRateResponseDto mapToResponse(MilkRate milkRate) {

        return MilkRateResponseDto.builder()
                .id(milkRate.getId())
                .animalType(milkRate.getAnimalType())
                .ratePerLiter(milkRate.getRatePerLiter())
                .effectiveFrom(milkRate.getEffectiveFrom())
                .effectiveTo(milkRate.getEffectiveTo())
                .createdAt(milkRate.getCreatedAt())
                .updatedAt(milkRate.getUpdatedAt())
                .build();
    }

    @Override
    public MilkRateResponseDto createMilkRate(MilkRateRequestDto request) {

        milkRateRepository
                .findByAnimalTypeAndEffectiveToIsNull(request.getAnimalType())
                .ifPresent(currentRate -> {

                    if (!request.getEffectiveFrom()
                            .isAfter(currentRate.getEffectiveFrom())) {

                        throw new BadRequestException(
                                "Effective from date must be after current rate.");
                    }

                    currentRate.setEffectiveTo(
                            request.getEffectiveFrom().minusDays(1));

                    milkRateRepository.save(currentRate);
                });

        MilkRate milkRate = MilkRate.builder()
                .animalType(request.getAnimalType())
                .ratePerLiter(request.getRatePerLiter())
                .effectiveFrom(request.getEffectiveFrom())
                .build();

        MilkRate savedRate = milkRateRepository.save(milkRate);

        return mapToResponse(savedRate);
    }

    @Override
    public MilkRateResponseDto updateMilkRate(Long id,
                                              MilkRateRequestDto request) {

        MilkRate milkRate = milkRateRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Milk rate not found"));

        milkRate.setAnimalType(request.getAnimalType());
        milkRate.setRatePerLiter(request.getRatePerLiter());
        milkRate.setEffectiveFrom(request.getEffectiveFrom());

        MilkRate updatedRate = milkRateRepository.save(milkRate);

        return mapToResponse(updatedRate);
    }

    @Override
    public MilkRateResponseDto getMilkRateById(Long id) {

        MilkRate milkRate = milkRateRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Milk rate not found"));

        return mapToResponse(milkRate);
    }

    @Override
    public List<MilkRateResponseDto> getAllMilkRates() {

        return milkRateRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public ResponseEntity<String> deleteMilkRate(Long id) {

        MilkRate milkRate = milkRateRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Milk rate not found"));

        milkRateRepository.delete(milkRate);

        return ResponseEntity.ok("Milk rate deleted successfully.");
    }

    @Override
    public MilkRateResponseDto getCurrentRate(AnimalType animalType) {

        MilkRate milkRate = milkRateRepository
                .findByAnimalTypeAndEffectiveToIsNull(animalType)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Current milkrecords rate not found"));

        return mapToResponse(milkRate);
    }

    @Override
    public List<MilkRateResponseDto> getRateHistory(AnimalType animalType) {

        return milkRateRepository
                .findByAnimalTypeOrderByEffectiveFromDesc(animalType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public MilkRateResponseDto getApplicableRate(
            AnimalType animalType,
            LocalDate date) {

        MilkRate milkRate = milkRateRepository
                .findApplicableRate(animalType, date)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No milkrecords rate found for the given date."));

        return mapToResponse(milkRate);
    }
}
