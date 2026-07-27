package com.sahil.dairy.milk_production.service;

import com.sahil.dairy.animal.entity.Animal;
import com.sahil.dairy.animal.repository.AnimalRepository;
import com.sahil.dairy.exception.DuplicateResourceException;
import com.sahil.dairy.exception.ResourceNotFoundException;
import com.sahil.dairy.milk_production.dto.MilkRequestDto;
import com.sahil.dairy.milk_production.dto.MilkResponseDto;
import com.sahil.dairy.milk_production.entity.MilkRecord;
import com.sahil.dairy.milk_production.repository.MilkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MilkServiceImpl implements MilkService {

    private final AnimalRepository animalRepository;
    private final MilkRepository milkRepository;

    private MilkResponseDto mapToResponse(MilkRecord milkRecord) {

        return MilkResponseDto.builder()
                .id(milkRecord.getId())
                .animalId(milkRecord.getAnimal().getId())
                .animalName(milkRecord.getAnimal().getName())
                .earTagNumber(milkRecord.getAnimal().getEarTagNumber())
                .animalType(milkRecord.getAnimal().getAnimalType())
                .milkDate(milkRecord.getMilkDate())
                .milkSession(milkRecord.getMilkSession())
                .quantityLiters(milkRecord.getQuantityLiters())
                .fatPercentage(milkRecord.getFatPercentage())
                .snfPercentage(milkRecord.getSnfPercentage())
                .notes(milkRecord.getNotes())
                .createdAt(milkRecord.getCreatedAt())
                .updatedAt(milkRecord.getUpdatedAt())
                .build();
    }

    @Override
    public MilkResponseDto createMilkRecord(MilkRequestDto request) {

        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found"));

        if (milkRepository.existsByAnimalIdAndMilkDateAndMilkSession(request.getAnimalId(),
                request.getMilkDate(),
                request.getMilkSession())) {

            throw new DuplicateResourceException(
                    "Milk record already exists for this animal, date and session");
        }

        MilkRecord milkRecord = MilkRecord.builder()
                .animal(animal)
                .milkDate(request.getMilkDate())
                .milkSession(request.getMilkSession())
                .quantityLiters(request.getQuantityLiters())
                .fatPercentage(request.getFatPercentage())
                .snfPercentage(request.getSnfPercentage())
                .notes(request.getNotes())
                .build();

        MilkRecord savedRecord = milkRepository.save(milkRecord);
        return mapToResponse(savedRecord);
    }

    @Override
    public MilkResponseDto updateMilkRecord(Long id, MilkRequestDto request) {
        MilkRecord milkRecord = milkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MilkRecord is not found"));

        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found "));

        if (!milkRecord.getAnimal().getId().equals(request.getAnimalId())
                || !milkRecord.getMilkDate().equals(request.getMilkDate())
                || !milkRecord.getMilkSession().equals(request.getMilkSession())) {

            if (milkRepository.existsByAnimalIdAndMilkDateAndMilkSession(
                    request.getAnimalId(),
                    request.getMilkDate(),
                    request.getMilkSession())) {

                throw new DuplicateResourceException(
                        "Milk record already exists for this animal, date and session");
            }
        }
// Setters
        milkRecord.setAnimal(animal);
        milkRecord.setMilkDate(request.getMilkDate());
        milkRecord.setMilkSession(request.getMilkSession());
        milkRecord.setQuantityLiters(request.getQuantityLiters());
        milkRecord.setFatPercentage(request.getFatPercentage());
        milkRecord.setSnfPercentage(request.getSnfPercentage());
        milkRecord.setNotes(request.getNotes());

        MilkRecord updatedRecord = milkRepository.save(milkRecord);

        return mapToResponse(updatedRecord);


    }


    @Override
    public MilkResponseDto getMilkRecordById(Long id) {

        MilkRecord milkRecord = milkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Milk record not found "));

        return mapToResponse(milkRecord);
    }

    @Override
    public List<MilkResponseDto> getAllMilkRecords() {
        List<MilkRecord> milkRecords = milkRepository.findAll();

        return milkRecords.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteMilkRecord(Long id) {

        MilkRecord milkRecord = milkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MilkRecord not found "));

        milkRepository.delete(milkRecord);


    }
}
