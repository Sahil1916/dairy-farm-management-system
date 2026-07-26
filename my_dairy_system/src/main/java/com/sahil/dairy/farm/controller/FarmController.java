package com.sahil.dairy.farm.controller;

import com.sahil.dairy.farm.dto.FarmRequestDto;
import com.sahil.dairy.farm.dto.FarmResponseDto;
import com.sahil.dairy.farm.services.FarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/farms")
@RequiredArgsConstructor
public class FarmController {
    private final FarmService farmService;

    @PostMapping
    public FarmResponseDto createFarm( @RequestBody FarmRequestDto request) {

        return  farmService.createFarm(request);

    }

    @GetMapping("/{id}")
    public FarmResponseDto getFarmById( @PathVariable Long id ){

        return farmService.getFarmById(id);

    }

    @GetMapping("/getallfarms")
    public List<FarmResponseDto> getAllFarms(){
        return farmService.getAllFarms();
    }

    @PutMapping("/{id}")
    public FarmResponseDto updateFarm(@PathVariable Long id ,@RequestBody FarmRequestDto request){
        return farmService.updateFarm(id ,request);
    }

    @DeleteMapping("/{id}")
    public void deleteFarm(@PathVariable Long id){
        farmService.deleteFarm(id);
    }
}