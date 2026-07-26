package com.sahil.dairy.animal.repository;


import com.sahil.dairy.animal.entity.Animal;
import com.sahil.dairy.animal.enums.AnimalStatus;
import com.sahil.dairy.animal.enums.AnimalType;
import com.sahil.dairy.animal.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Optional<Animal> findByEarTagNumber(String earTagNumber);

    boolean existsByEarTagNumber(String earTagNumber);

    // Uses idx_animal_farm
    List<Animal> findByFarmId(Long farmId);

//    List<Animal> findByFarmIdAndAnimalStatus(Long farmId, Animal animalStatus);

    List<Animal> findByFarmIdAndAnimalStatus(
            Long farmId,
            AnimalStatus animalStatus
    );
    List<Animal> findByFarmIdAndExpectedCalvingDateBetween(
            Long farmId, LocalDate startDate, LocalDate endDate);

    List<Animal> findByFarmIdAndAnimalType(
            Long farmId,
            AnimalType animalType
    );


    List<Animal> findByGender(Gender gender);

    List<Animal> findByBreed(String breed);

    List<Animal> findByAnimalStatus(AnimalStatus animalStatus);

    long countByFarmId(Long farmId);

    long countByFarmIdAndAnimalStatus(Long farmId, AnimalStatus status);
}
