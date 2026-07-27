package com.sahil.dairy.milk_production.service;

import com.sahil.dairy.milk_production.dto.MilkRequestDto;
import com.sahil.dairy.milk_production.dto.MilkResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MilkService {

    MilkResponseDto createMilkRecord(MilkRequestDto request);

    MilkResponseDto updateMilkRecord(Long id, MilkRequestDto request);

    MilkResponseDto getMilkRecordById(Long id);

    List<MilkResponseDto> getAllMilkRecords();

    void deleteMilkRecord(Long id);

}