package com.sahil.dairy.milkrecords.service;

import com.sahil.dairy.milkrecords.dto.MilkRequestDto;
import com.sahil.dairy.milkrecords.dto.MilkResponseDto;
import com.sahil.dairy.milkrecords.dto.TodayMilkSummaryDto;

import java.time.LocalDate;
import java.util.List;

public interface MilkService {

    MilkResponseDto createMilkRecord(MilkRequestDto request);

    MilkResponseDto updateMilkRecord(Long id, MilkRequestDto request);

    MilkResponseDto getMilkRecordById(Long id);

    List<MilkResponseDto> getAllMilkRecords();

    void deleteMilkRecord(Long id);

    TodayMilkSummaryDto getTodayMilkSummary();

    TodayMilkSummaryDto getMonthlyMilkSummary(int month, int year);

    List<MilkResponseDto> getMilkHistoryByAnimal(Long animalId);

    TodayMilkSummaryDto getDateRangeMilkSummary(
            LocalDate startDate,
            LocalDate endDate
    );

    TodayMilkSummaryDto getYearlyMilkSummary(int year);
}
