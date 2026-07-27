package com.sahil.dairy.milk_production.controller;

import com.sahil.dairy.milk_production.dto.MilkRequestDto;
import com.sahil.dairy.milk_production.dto.MilkResponseDto;
import com.sahil.dairy.milk_production.service.MilkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/milkrecords")
public class MilkRecordController {

    private final MilkService milkService;

    @PostMapping
    public MilkResponseDto createMilkRecord(
            @Valid @RequestBody MilkRequestDto request) {

        return milkService.createMilkRecord(request);
    }

    @PutMapping("/{id}")
    public MilkResponseDto updateMilkRecord(@PathVariable Long id, @Valid @RequestBody
    MilkRequestDto request) {
        return milkService.updateMilkRecord(id, request);
    }

    @GetMapping("/{id}")
    public MilkResponseDto getMilkRecordById(@PathVariable Long id) {

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
}
