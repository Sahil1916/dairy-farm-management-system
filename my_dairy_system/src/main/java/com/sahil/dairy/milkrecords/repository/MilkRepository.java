package com.sahil.dairy.milkrecords.repository;

import com.sahil.dairy.animal.enums.AnimalType;
import com.sahil.dairy.milkrecords.entity.MilkRecord;
import com.sahil.dairy.milkrecords.enums.MilkSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    @Query("""
       SELECT COALESCE(SUM(m.quantityLiters), 0)
       FROM MilkRecord m
       WHERE m.milkDate = :date
       AND m.animal.animalType = com.sahil.dairy.animal.enums.AnimalType.COW
       """)
    BigDecimal getTodayCowMilk(@Param("date") LocalDate date);

    @Query("""
       SELECT COALESCE(SUM(m.quantityLiters), 0)
       FROM MilkRecord m
       WHERE m.milkDate = :date
       AND m.animal.animalType = com.sahil.dairy.animal.enums.AnimalType.BUFFALO
       """)
    BigDecimal getTodayBuffaloMilk(@Param("date") LocalDate date);

    @Query("""
    SELECT COALESCE(SUM(m.quantityLiters), 0)
    FROM MilkRecord m
    WHERE MONTH(m.milkDate) = :month
      AND YEAR(m.milkDate) = :year
      AND m.animal.animalType = :animalType
    """)
    BigDecimal getMonthlyMilkByAnimalType(
            @Param("month") int month,
            @Param("year") int year,
            @Param("animalType") AnimalType animalType
    );

    List<MilkRecord> findByAnimalIdOrderByMilkDateDesc(Long animalId);

    @Query("""
    SELECT COALESCE(SUM(m.quantityLiters), 0)
    FROM MilkRecord m
    WHERE m.milkDate BETWEEN :startDate AND :endDate
      AND m.animal.animalType = :animalType
    """)
    BigDecimal getMilkByDateRangeAndAnimalType(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("animalType") AnimalType animalType
    );

    @Query("""
    SELECT COALESCE(SUM(m.quantityLiters),0)
    FROM MilkRecord m
    WHERE YEAR(m.milkDate)=:year
      AND m.animal.animalType=:animalType
    """)
    BigDecimal getYearlyMilkByAnimalType(
            int year,
            AnimalType animalType
    );
}