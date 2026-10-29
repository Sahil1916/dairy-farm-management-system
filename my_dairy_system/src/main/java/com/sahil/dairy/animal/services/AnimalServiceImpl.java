package com.sahil.dairy.animal.services;

import com.sahil.dairy.animal.dto.AnimalRequestDto;
import com.sahil.dairy.animal.dto.AnimalResponseDto;
import com.sahil.dairy.animal.entity.Animal;
import com.sahil.dairy.animal.repository.AnimalRepository;
import com.sahil.dairy.exception.DuplicateResourceException;
import com.sahil.dairy.exception.ResourceNotFoundException;
import com.sahil.dairy.farm.entity.Farm;
import com.sahil.dairy.farm.repository.FarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final FarmRepository farmRepository;

    private AnimalResponseDto mapToResponse(Animal animal) {

        return AnimalResponseDto.builder()
                .id(animal.getId())
                .farmId(animal.getFarm().getId())
                .farmName(animal.getFarm().getFarmName())
                .earTagNumber(animal.getEarTagNumber())
                .name(animal.getName())
                .animalType(animal.getAnimalType())
                .breed(animal.getBreed())
                .gender(animal.getGender())
                .dateOfBirth(animal.getDateOfBirth())
                .weightKg(animal.getWeightKg())
                .animalStatus(animal.getAnimalStatus())
                .lactationStatus(animal.getLactationStatus())
                .purchaseDate(animal.getPurchaseDate())
                .purchasePrice(animal.getPurchasePrice())
                .sellingDate(animal.getSellingDate())
                .sellingPrice(animal.getSellingPrice())
                .lastCalvingDate(animal.getLastCalvingDate())
                .expectedCalvingDate(animal.getExpectedCalvingDate())
                .createdAt(animal.getCreatedAt())
                .updatedAt(animal.getUpdatedAt())
                .build();
    }


    @Override
    public AnimalResponseDto createAnimal(AnimalRequestDto request) {

        Farm farm = farmRepository.findById(request.getFarmId())
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found "));


        if (animalRepository.existsByEarTagNumber(request.getEarTagNumber())) {
            throw new DuplicateResourceException(" Animal already exist");
        }

        Animal animal = Animal.builder()
                .farm(farm)
                .earTagNumber(request.getEarTagNumber())
                .name(request.getName())
                .animalType(request.getAnimalType())
                .breed(request.getBreed())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .weightKg(request.getWeightKg())
                .animalStatus(request.getAnimalStatus())
                .lactationStatus(request.getLactationStatus())
                .purchaseDate(request.getPurchaseDate())
                .purchasePrice(request.getPurchasePrice())
                .sellingDate(request.getSellingDate())
                .sellingPrice(request.getSellingPrice())
                .lastCalvingDate(request.getLastCalvingDate())
                .expectedCalvingDate(request.getExpectedCalvingDate())
                .build();

        Animal savedAnimal = animalRepository.save(animal);

        return mapToResponse(savedAnimal);
    }


    @Override
    public AnimalResponseDto updateAnimal(Long id, AnimalRequestDto request) {

        Animal animal = animalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Animal not found"));

        Farm farm = farmRepository.findById(request.getFarmId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Farm not found"));

        animal.setFarm(farm);
        //  animal.setEarTagNumber(request.getEarTagNumber());
        animal.setName(request.getName());
        animal.setAnimalType(request.getAnimalType());
        animal.setBreed(request.getBreed());
        animal.setGender(request.getGender());
        animal.setDateOfBirth(request.getDateOfBirth());
        animal.setWeightKg(request.getWeightKg());
        animal.setAnimalStatus(request.getAnimalStatus());
        animal.setLactationStatus(request.getLactationStatus());
        animal.setPurchaseDate(request.getPurchaseDate());
        animal.setPurchasePrice(request.getPurchasePrice());
        animal.setSellingDate(request.getSellingDate());
        animal.setSellingPrice(request.getSellingPrice());
        animal.setLastCalvingDate(request.getLastCalvingDate());
        animal.setExpectedCalvingDate(request.getExpectedCalvingDate());

        Animal updatedAnimal = animalRepository.save(animal);

        return mapToResponse(updatedAnimal);
    }

    @Override
    public AnimalResponseDto getAnimalById(Long id) {

        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found "));

        return mapToResponse(animal);
    }

    @Override
    public List<AnimalResponseDto> getAllAnimals() {

        List<Animal> animals = animalRepository.findAll();

        return animals.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteAnimal(Long id) {

        Animal animal = animalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Animal not found"));

        animalRepository.delete(animal);
    }
}
