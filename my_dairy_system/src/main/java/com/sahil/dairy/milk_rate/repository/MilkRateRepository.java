package com.sahil.dairy.milk_rate.repository;

import com.sahil.dairy.animal.enums.AnimalType;
import com.sahil.dairy.milk_rate.entity.MilkRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MilkRateRepository extends JpaRepository<MilkRate, Long> {

    // Get current active rate
    Optional<MilkRate> findByAnimalTypeAndEffectiveToIsNull(AnimalType animalType);

    // Get complete rate history
    List<MilkRate> findByAnimalTypeOrderByEffectiveFromDesc(AnimalType animalType);

    // Get rate for a specific date
    @Query("""
            SELECT mr
            FROM MilkRate mr
            WHERE mr.animalType = :animalType
              AND :date BETWEEN mr.effectiveFrom
              AND COALESCE(mr.effectiveTo, :date)
            """)
    Optional<MilkRate> findApplicableRate(
            AnimalType animalType,
            LocalDate date);
}