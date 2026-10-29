package com.sahil.dairy.farm.services;

import com.sahil.dairy.exception.DuplicateResourceException;
import com.sahil.dairy.exception.ResourceNotFoundException;
import com.sahil.dairy.farm.dto.FarmRequestDto;
import com.sahil.dairy.farm.dto.FarmResponseDto;
import com.sahil.dairy.farm.entity.Farm;
import com.sahil.dairy.farm.repository.FarmRepository;
import com.sahil.dairy.user.entity.User;
import com.sahil.dairy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    @Override
    public FarmResponseDto createFarm(FarmRequestDto request) {

        if (farmRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

        if (farmRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone already exists.");
        }

        // Logged-in Owner
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Owner not found"));

        // One owner = One farm
        if (owner.getFarm() != null) {
            throw new DuplicateResourceException(
                    "This owner already has a farm."
            );
        }

        Farm farm = Farm.builder()
                .farmName(request.getFarmName())

                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .totalLandAcres(request.getTotalLandAcres())
                .build();

        Farm savedFarm = farmRepository.save(farm);

        // Assign farm to owner
        owner.setFarm(savedFarm);
        userRepository.save(owner);

        return FarmResponseDto.builder()
                .id(savedFarm.getId())
                .farmName(savedFarm.getFarmName())

                .phone(savedFarm.getPhone())
                .email(savedFarm.getEmail())
                .address(savedFarm.getAddress())
                .totalLandAcres(savedFarm.getTotalLandAcres())
                .createdAt(savedFarm.getCreatedAt())
                .updatedAt(savedFarm.getUpdatedAt())
                .build();
    }

    @Override
    public FarmResponseDto getFarmById(Long id) {

        Farm farm = farmRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Farm not found"));

        return FarmResponseDto.builder()
                .id(farm.getId())
                .farmName(farm.getFarmName())
                .phone(farm.getPhone())
                .email(farm.getEmail())
                .address(farm.getAddress())
                .totalLandAcres(farm.getTotalLandAcres())
                .createdAt(farm.getCreatedAt())
                .updatedAt(farm.getUpdatedAt())
                .build();
    }

    @Override
    public List<FarmResponseDto> getAllFarms() {

        return farmRepository.findAll()
                .stream()
                .map(farm -> FarmResponseDto.builder()
                        .id(farm.getId())
                        .farmName(farm.getFarmName())
                        .phone(farm.getPhone())
                        .email(farm.getEmail())
                        .address(farm.getAddress())
                        .totalLandAcres(farm.getTotalLandAcres())
                        .createdAt(farm.getCreatedAt())
                        .updatedAt(farm.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public FarmResponseDto updateFarm(Long id, FarmRequestDto request) {

        Farm farm = farmRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Farm not found"));

        farm.setFarmName(request.getFarmName());
        farm.setPhone(request.getPhone());
        farm.setEmail(request.getEmail());
        farm.setAddress(request.getAddress());
        farm.setTotalLandAcres(request.getTotalLandAcres());

        Farm updatedFarm = farmRepository.save(farm);

        return FarmResponseDto.builder()
                .id(updatedFarm.getId())
                .farmName(updatedFarm.getFarmName())
                .phone(updatedFarm.getPhone())
                .email(updatedFarm.getEmail())
                .address(updatedFarm.getAddress())
                .totalLandAcres(updatedFarm.getTotalLandAcres())
                .createdAt(updatedFarm.getCreatedAt())
                .updatedAt(updatedFarm.getUpdatedAt())
                .build();
    }

    @Override
    public ResponseEntity<String> deleteFarm(Long id) {

        Farm farm = farmRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Farm not found"));

        farmRepository.delete(farm);

        return ResponseEntity.ok("Farm deleted successfully");
    }
}