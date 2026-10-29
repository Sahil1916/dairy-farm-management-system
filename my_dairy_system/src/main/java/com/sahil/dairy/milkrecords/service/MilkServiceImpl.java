package com.sahil.dairy.milkrecords.service;

import com.sahil.dairy.AgentDebugLog;
import com.sahil.dairy.animal.entity.Animal;
import com.sahil.dairy.animal.enums.AnimalType;
import com.sahil.dairy.animal.enums.LactationStatus;
import com.sahil.dairy.animal.repository.AnimalRepository;
import com.sahil.dairy.exception.BadRequestException;
import com.sahil.dairy.exception.DuplicateResourceException;
import com.sahil.dairy.exception.ResourceNotFoundException;
import com.sahil.dairy.milkrecords.dto.MilkRequestDto;
import com.sahil.dairy.milkrecords.dto.MilkResponseDto;
import com.sahil.dairy.milkrecords.dto.TodayMilkSummaryDto;
import com.sahil.dairy.milkrecords.entity.MilkRecord;
import com.sahil.dairy.milkrecords.repository.MilkRepository;
import com.sahil.dairy.milk_rate.entity.MilkRate;
import com.sahil.dairy.milk_rate.repository.MilkRateRepository;
import lombok.RequiredArgsConstructor;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MilkServiceImpl implements MilkService {


    private final AnimalRepository animalRepository;

    private final MilkRepository milkRepository;

    private final MilkRateRepository milkRateRepository;

    // #region agent log
    @PostConstruct
    void agentDebugLoaded() {
        debugLog("F", "MilkServiceImpl.init", "instrumentation loaded",
                "{\"userDir\":\"" + System.getProperty("user.dir").replace("\\", "/") + "\"}");
    }

    private void debugLog(String hypothesisId, String location, String message, String dataJson) {
        AgentDebugLog.log(hypothesisId, location, message, dataJson);
    }

    private String qtyMeta(String name, BigDecimal value) {
        return "\"" + name + "\":" + (value == null ? "null" : value.toPlainString())
                + ",\"" + name + "Class\":"
                + (value == null ? "null" : "\"" + value.getClass().getName() + "\"");
    }
    // #endregion

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
                .ratePerLiter(milkRecord.getRatePerLiter())
                .totalAmount(milkRecord.getTotalAmount())
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
        if (animal.getLactationStatus() == LactationStatus.DRY) {
            throw new BadRequestException(
                    "Milk cannot be recorded for a dry animal.");
        }
        MilkRate milkRate = milkRateRepository
                .findApplicableRate(
                        animal.getAnimalType(),
                        request.getMilkDate())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Milk rate not found for this date."));

        // Calculate amount
        BigDecimal totalAmount = request.getQuantityLiters()
                .multiply(milkRate.getRatePerLiter())
                .setScale(2, RoundingMode.HALF_UP);

        MilkRecord milkRecord = MilkRecord.builder()
                .animal(animal)
                .milkDate(request.getMilkDate())
                .milkSession(request.getMilkSession())
                .quantityLiters(request.getQuantityLiters())
                .fatPercentage(request.getFatPercentage())
                .snfPercentage(request.getSnfPercentage())
                .ratePerLiter(milkRate.getRatePerLiter())
                .totalAmount(totalAmount)
                .notes(request.getNotes())
                .build();

        MilkRecord savedRecord = milkRepository.save(milkRecord);
        // #region agent log
        debugLog("D", "MilkServiceImpl.createMilkRecord", "milk record amount calculated",
                "{\"animalId\":" + animal.getId()
                        + ",\"animalType\":\"" + animal.getAnimalType() + "\""
                        + ",\"milkDate\":\"" + request.getMilkDate() + "\""
                        + ",\"session\":\"" + request.getMilkSession() + "\""
                        + "," + qtyMeta("quantityLiters", request.getQuantityLiters())
                        + "," + qtyMeta("ratePerLiter", milkRate.getRatePerLiter())
                        + "," + qtyMeta("totalAmount", totalAmount)
                        + ",\"fatPercentage\":" + (request.getFatPercentage() == null ? "null" : request.getFatPercentage().toPlainString())
                        + ",\"snfPercentage\":" + (request.getSnfPercentage() == null ? "null" : request.getSnfPercentage().toPlainString())
                        + ",\"savedId\":" + savedRecord.getId() + "}");
        // #endregion
        return mapToResponse(savedRecord);
    }
    @Override
    public MilkResponseDto updateMilkRecord(Long id, MilkRequestDto request) {

        MilkRecord milkRecord = milkRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Milk record not found"));

        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Animal not found"));

        // Business Rule: Dry animal cannot have milkrecords entry
        if (animal.getLactationStatus() == LactationStatus.DRY) {
            throw new BadRequestException(
                    "Milk cannot be updated for a dry animal.");
        }

        // Duplicate validation
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

        // Find applicable milkrecords rate
        MilkRate milkRate = milkRateRepository
                .findApplicableRate(
                        animal.getAnimalType(),
                        request.getMilkDate())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Milk rate not found for this date."));

        // Calculate amount
        BigDecimal totalAmount = request.getQuantityLiters()
                .multiply(milkRate.getRatePerLiter())
                .setScale(2, RoundingMode.HALF_UP);

        // Update entity
        milkRecord.setAnimal(animal);
        milkRecord.setMilkDate(request.getMilkDate());
        milkRecord.setMilkSession(request.getMilkSession());
        milkRecord.setQuantityLiters(request.getQuantityLiters());
        milkRecord.setFatPercentage(request.getFatPercentage());
        milkRecord.setSnfPercentage(request.getSnfPercentage());
        milkRecord.setRatePerLiter(milkRate.getRatePerLiter());
        milkRecord.setTotalAmount(totalAmount);
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

    @Override
    public TodayMilkSummaryDto getTodayMilkSummary() {
        LocalDate today = LocalDate.now();
        BigDecimal cowMilk;
        BigDecimal buffaloMilk;
        try {
            cowMilk = milkRepository.getTodayCowMilk(today);
            buffaloMilk = milkRepository.getTodayBuffaloMilk(today);
            // #region agent log
            debugLog("A,B,C", "MilkServiceImpl.getTodayMilkSummary", "today summary query result",
                    "{\"today\":\"" + today + "\""
                            + ",\"zone\":\"" + java.time.ZoneId.systemDefault() + "\""
                            + "," + qtyMeta("cowMilk", cowMilk)
                            + "," + qtyMeta("buffaloMilk", buffaloMilk) + "}");
            // #endregion
        } catch (Exception e) {
            // #region agent log
            debugLog("B,C", "MilkServiceImpl.getTodayMilkSummary", "today summary query failed",
                    "{\"today\":\"" + today + "\",\"ex\":\"" + e.getClass().getName()
                            + "\",\"msg\":\"" + String.valueOf(e.getMessage()).replace("\"", "'") + "\"}");
            // #endregion
            throw e;
        }

        BigDecimal totalMilk = cowMilk.add(buffaloMilk);

        return TodayMilkSummaryDto.builder()
                .cowMilk(cowMilk)
                .buffaloMilk(buffaloMilk)
                .totalMilk(totalMilk)
                .build();
    }

    @Override
    public TodayMilkSummaryDto getMonthlyMilkSummary(int month, int year) {

        BigDecimal cowMilk;
        BigDecimal buffaloMilk;
        try {
            cowMilk =
                    milkRepository.getMonthlyMilkByAnimalType(
                            month,
                            year,
                            AnimalType.COW);

            buffaloMilk =
                    milkRepository.getMonthlyMilkByAnimalType(
                            month,
                            year,
                            AnimalType.BUFFALO);
            // #region agent log
            debugLog("E", "MilkServiceImpl.getMonthlyMilkSummary", "monthly summary query result",
                    "{\"month\":" + month + ",\"year\":" + year
                            + "," + qtyMeta("cowMilk", cowMilk)
                            + "," + qtyMeta("buffaloMilk", buffaloMilk) + "}");
            // #endregion
        } catch (Exception e) {
            // #region agent log
            debugLog("E", "MilkServiceImpl.getMonthlyMilkSummary", "monthly summary query failed",
                    "{\"month\":" + month + ",\"year\":" + year
                            + ",\"ex\":\"" + e.getClass().getName()
                            + "\",\"msg\":\"" + String.valueOf(e.getMessage()).replace("\"", "'") + "\"}");
            // #endregion
            throw e;
        }

        BigDecimal totalMilk = cowMilk.add(buffaloMilk);

        return TodayMilkSummaryDto.builder()
                .cowMilk(cowMilk)
                .buffaloMilk(buffaloMilk)
                .totalMilk(totalMilk)
                .build();
    }

    @Override
    public List<MilkResponseDto> getMilkHistoryByAnimal(Long animalId) {

        if (!animalRepository.existsById(animalId)) {
            throw new ResourceNotFoundException("Animal not found");
        }

        List<MilkRecord> milkRecords =
                milkRepository.findByAnimalIdOrderByMilkDateDesc(animalId);

        return milkRecords.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TodayMilkSummaryDto getDateRangeMilkSummary(
            LocalDate startDate,
            LocalDate endDate) {

        BigDecimal cowMilk;
        BigDecimal buffaloMilk;
        try {
            cowMilk = milkRepository.getMilkByDateRangeAndAnimalType(
                    startDate,
                    endDate,
                    AnimalType.COW);

            buffaloMilk = milkRepository.getMilkByDateRangeAndAnimalType(
                    startDate,
                    endDate,
                    AnimalType.BUFFALO);
            // #region agent log
            debugLog("A,C", "MilkServiceImpl.getDateRangeMilkSummary", "date-range summary query result",
                    "{\"startDate\":\"" + startDate + "\",\"endDate\":\"" + endDate + "\""
                            + "," + qtyMeta("cowMilk", cowMilk)
                            + "," + qtyMeta("buffaloMilk", buffaloMilk) + "}");
            // #endregion
        } catch (Exception e) {
            // #region agent log
            debugLog("B,C", "MilkServiceImpl.getDateRangeMilkSummary", "date-range summary query failed",
                    "{\"startDate\":\"" + startDate + "\",\"endDate\":\"" + endDate
                            + "\",\"ex\":\"" + e.getClass().getName()
                            + "\",\"msg\":\"" + String.valueOf(e.getMessage()).replace("\"", "'") + "\"}");
            // #endregion
            throw e;
        }

        BigDecimal totalMilk = cowMilk.add(buffaloMilk);

        return TodayMilkSummaryDto.builder()
                .cowMilk(cowMilk)
                .buffaloMilk(buffaloMilk)
                .totalMilk(totalMilk)
                .build();
    }

    @Override
    public TodayMilkSummaryDto getYearlyMilkSummary(int year) {

        BigDecimal cowMilk = milkRepository.getYearlyMilkByAnimalType(
                year,
                AnimalType.COW);

        BigDecimal buffaloMilk = milkRepository.getYearlyMilkByAnimalType(
                year,
                AnimalType.BUFFALO);

        BigDecimal totalMilk = cowMilk.add(buffaloMilk);

        return TodayMilkSummaryDto.builder()
                .cowMilk(cowMilk)
                .buffaloMilk(buffaloMilk)
                .totalMilk(totalMilk)
                .build();
    }

}
