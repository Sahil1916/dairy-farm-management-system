package com.sahil.dairy.milk_production.repository;

import com.sahil.dairy.milk_production.entity.MilkRecord;
import com.sahil.dairy.milk_production.enums.MilkSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MilkRepository extends JpaRepository<MilkRecord, Long> {

    // Duplicate Check
    boolean existsByAnimalIdAndMilkDateAndMilkSession(
            Long animalId,
            LocalDate milkDate,
            MilkSession milkSession
    );

    // Animal Wise
    List<MilkRecord> findByAnimalId(Long animalId);

    // Date Wise
    List<MilkRecord> findByMilkDate(LocalDate milkDate);

    // Animal + Date
    List<MilkRecord> findByAnimalIdAndMilkDate(
            Long animalId,
            LocalDate milkDate
    );

    // Animal + Date Range
    List<MilkRecord> findByAnimalIdAndMilkDateBetween(
            Long animalId,
            LocalDate startDate,
            LocalDate endDate
    );

    // Farm Wise
    List<MilkRecord> findByAnimalFarmId(Long farmId);

    // Farm + Date
    List<MilkRecord> findByAnimalFarmIdAndMilkDate(
            Long farmId,
            LocalDate milkDate
    );

    // Farm + Date Range
    List<MilkRecord> findByAnimalFarmIdAndMilkDateBetween(
            Long farmId,
            LocalDate startDate,
            LocalDate endDate
    );

    // Session Wise
    List<MilkRecord> findByMilkSession(MilkSession milkSession);

    // Animal + Session
    List<MilkRecord> findByAnimalIdAndMilkSession(
            Long animalId,
            MilkSession milkSession
    );
}