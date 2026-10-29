package com.sahil.dairy.milkrecords.controller;

import com.sahil.dairy.milkrecords.dto.MilkRequestDto;
import com.sahil.dairy.milkrecords.dto.MilkResponseDto;
import com.sahil.dairy.milkrecords.dto.TodayMilkSummaryDto;
import com.sahil.dairy.milkrecords.service.MilkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/milkrecords")
public class MilkRecordController {

    private final MilkService milkService;

    @PostMapping
    public MilkResponseDto createMilkRecord(
            @Valid @RequestBody MilkRequestDto request) {
        // #region agent log
        com.sahil.dairy.AgentDebugLog.log("D,F", "MilkRecordController.createMilkRecord", "create hit",
                "{\"animalId\":" + request.getAnimalId()
                        + ",\"milkDate\":\"" + request.getMilkDate() + "\""
                        + ",\"quantityLiters\":" + (request.getQuantityLiters() == null ? "null" : request.getQuantityLiters().toPlainString())
                        + "}");
        // #endregion
        return milkService.createMilkRecord(request);
    }

    @PutMapping("/{id}")
    public MilkResponseDto updateMilkRecord(@PathVariable Long id, @Valid @RequestBody
    MilkRequestDto request) {
        return milkService.updateMilkRecord(id, request);
    }

    @GetMapping("/{id}")
    public MilkResponseDto getMilkRecordById(@PathVariable Long id) {
        // #region agent log
        com.sahil.dairy.AgentDebugLog.log("F", "MilkRecordController.getMilkRecordById", "get-by-id hit",
                "{\"id\":" + id + "}");
        // #endregion
        return milkService.getMilkRecordById(id);
    }

    @GetMapping
    public List<MilkResponseDto> getAllMilkRecords() {

        return milkService.getAllMilkRecords();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteMilkRecord(@PathVariable Long id){

        milkService.deleteMilkRecord(id);
        return ResponseEntity.ok("MilkRecord delete successfulyy");
    }

    @GetMapping("/todays-summary")
    public TodayMilkSummaryDto getTodaysMilkSummary(){
        // #region agent log
        com.sahil.dairy.AgentDebugLog.log("A,F", "MilkRecordController.getTodaysMilkSummary", "todays-summary hit",
                "{}");
        // #endregion
      return  milkService.getTodayMilkSummary();
    }

    @GetMapping("/monthly-summary")
    public TodayMilkSummaryDto getMonthlyMilkSummary(
            @RequestParam int month,
            @RequestParam int year) {

        return milkService.getMonthlyMilkSummary(month, year);
    }
    @GetMapping("/animal/{animalId}")
    public List<MilkResponseDto> getMilkHistoryByAnimal(
            @PathVariable Long animalId) {

        return milkService.getMilkHistoryByAnimal(animalId);
    }

    @GetMapping("/date-range-summary")
    public TodayMilkSummaryDto getDateRangeMilkSummary(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        return milkService.getDateRangeMilkSummary(startDate, endDate);
    }
    @GetMapping("/yearly-summary")
    public TodayMilkSummaryDto getYearlyMilkSummary(
            @RequestParam int year) {

        return milkService.getYearlyMilkSummary(year);
    }
}
