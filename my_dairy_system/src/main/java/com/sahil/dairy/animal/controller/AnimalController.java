package com.sahil.dairy.animal.controller;

import com.sahil.dairy.animal.dto.AnimalRequestDto;
import com.sahil.dairy.animal.dto.AnimalResponseDto;
import com.sahil.dairy.animal.services.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping
    public AnimalResponseDto createAnimal(@Valid @RequestBody AnimalRequestDto request) {

        return animalService.createAnimal(request);
    }

    @PutMapping("/{id}")
    public AnimalResponseDto updateAnimal(@PathVariable Long id, @Valid
    @RequestBody AnimalRequestDto request) {

        return animalService.updateAnimal(id, request);
    }

    @GetMapping
    public List<AnimalResponseDto> getAllAnimals() {

        return animalService.getAllAnimals();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable Long id) {

        animalService.deleteAnimal(id);

        return ResponseEntity.ok("Animal deleted successfully");
    }

    @GetMapping("/{id}")
    public AnimalResponseDto getAnimalById(@PathVariable Long id) {

        return animalService.getAnimalById(id);
    }
}
