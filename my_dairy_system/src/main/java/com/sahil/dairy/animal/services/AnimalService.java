package com.sahil.dairy.animal.services;


import com.sahil.dairy.animal.dto.AnimalRequestDto;
import com.sahil.dairy.animal.dto.AnimalResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AnimalService {


    // CRUD
    AnimalResponseDto createAnimal(AnimalRequestDto request);

    AnimalResponseDto updateAnimal(Long id, AnimalRequestDto request);

    AnimalResponseDto getAnimalById(Long id);

    List<AnimalResponseDto> getAllAnimals();

   void deleteAnimal(Long id);


}
//    // Farm Wise
//    List<Animal> getAnimalsByFarm(Long farmId);
//
//    // Status Wise
//    List<Animal> getAnimalsByStatus(Long farmId, AnimalStatus status);
//
//    // Type Wise
//    List<Animal> getAnimalsByType(Long farmId, AnimalType animalType);
//
//    // Business Features
//    List<Animal> getPregnantAnimals(Long farmId);
//
//    List<Animal> getLactatingAnimals(Long farmId);
//
//    List<Animal> getDryAnimals(Long farmId);
//
//    List<Animal> getAnimalsDueForCalving(
//            Long farmId,
//            LocalDate startDate,
//            LocalDate endDate
//    );
//
//    // Lifecycle
//    Animal markAsPregnant(Long animalId);
//
//    Animal markAsLactating(Long animalId);
//
//    Animal markAsDry(Long animalId);
//
//    Animal markAsSold(Long animalId,
//                      BigDecimal sellingPrice,
//                      LocalDate sellingDate);
//
//    Animal markAsDead(Long animalId);
//
//    // Statistics
//    long getTotalAnimals(Long farmId);
//
//    long getTotalActiveAnimals(Long farmId);
//
//    long getTotalPregnantAnimals(Long farmId);
//
//    long getTotalLactatingAnimals(Long farmId);
//}

