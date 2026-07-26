package com.sahil.dairy.farm.services;

import com.sahil.dairy.exception.DuplicateResourceException;
import com.sahil.dairy.exception.ResourceNotFoundException;
import com.sahil.dairy.farm.dto.FarmRequestDto;
import com.sahil.dairy.farm.dto.FarmResponseDto;
import com.sahil.dairy.farm.entity.Farm;
import com.sahil.dairy.farm.repository.FarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;

    @Override
    public FarmResponseDto createFarm(FarmRequestDto request) {

        if (farmRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");         //   System.out.println("the email is already exist");
        }
        if (farmRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone already exists");
        }
        Farm farm = Farm.builder()
                .farmName(request.getFarmName())
                .ownerName(request.getOwnerName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .totalLandAcres(request.getTotalLandAcres())
                .build();

        Farm savedFarm = farmRepository.save(farm);

        FarmResponseDto response = FarmResponseDto.builder()
                .id(savedFarm.getId())
                .farmName(savedFarm.getFarmName())
                .ownerName(savedFarm.getOwnerName())
                .phone(savedFarm.getPhone())
                .email(savedFarm.getEmail())
                .address(savedFarm.getAddress())
                .totalLandAcres(savedFarm.getTotalLandAcres())
                .createdAt(savedFarm.getCreatedAt())
                .updatedAt(savedFarm.getUpdatedAt())
                .build();
        return response;
    }

    @Override
    public FarmResponseDto getFarmById(Long id) {

        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found"));

        FarmResponseDto response = FarmResponseDto.builder()
                .id(farm.getId())
                .farmName(farm.getFarmName())
                .ownerName(farm.getOwnerName())
                .phone(farm.getPhone())
                .email(farm.getEmail())
                .address(farm.getAddress())
                .totalLandAcres(farm.getTotalLandAcres())
                .createdAt(farm.getCreatedAt())
                .updatedAt(farm.getUpdatedAt())
                .build();

        return response;
    }

    @Override
    public List<FarmResponseDto> getAllFarms() {

        List<Farm> farms = farmRepository.findAll();

        return farms.stream()
                .map(farm -> FarmResponseDto.builder()
                        .id(farm.getId())
                        .farmName(farm.getFarmName())
                        .ownerName(farm.getOwnerName())
                        .phone(farm.getPhone())
                        .email(farm.getEmail())
                        .address(farm.getAddress())
                        .totalLandAcres(farm.getTotalLandAcres())
                        .createdAt(farm.getCreatedAt())
                        .updatedAt(farm.getUpdatedAt())
                        .build()
                )
                .toList();


    }

    @Override
    public FarmResponseDto updateFarm(Long id, FarmRequestDto request) {

        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found "));

        farm.setFarmName(request.getFarmName());
        farm.setOwnerName(request.getOwnerName());
        farm.setPhone(request.getPhone());
        farm.setEmail(request.getEmail());
        farm.setAddress(request.getAddress());
        farm.setTotalLandAcres(request.getTotalLandAcres());

        farmRepository.save(farm);

        FarmResponseDto response = FarmResponseDto.builder()
                .id(farm.getId())
                .farmName(farm.getFarmName())
                .ownerName(farm.getOwnerName())
                .phone(farm.getPhone())
                .email(farm.getEmail())
                .address(farm.getAddress())
                .totalLandAcres(farm.getTotalLandAcres())
                .createdAt(farm.getCreatedAt())
                .updatedAt(farm.getUpdatedAt())
                .build();

        return response;
    }

    @Override
    public ResponseEntity<String> deleteFarm(Long id) {

        Farm farm = farmRepository.findById(id)
                .orElseThrow(()->  new RuntimeException("farm not found"));

        farmRepository.delete(farm);
        return ResponseEntity.ok("Farm deleted successfully");
    }
}
