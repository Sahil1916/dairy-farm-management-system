package com.sahil.dairy.setup.controller;

import com.sahil.dairy.setup.InitialSetupRequest;
import com.sahil.dairy.setup.InitialSetupResponse;
import com.sahil.dairy.setup.service.InitialSetupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/setup")
@RequiredArgsConstructor
public class InitialSetupController {

    // Service handles all initial setup business logic
    private final InitialSetupService initialSetupService;

    // Checks whether initial OWNER setup is already completed
    @GetMapping("/status")
    public ResponseEntity<Boolean> getSetupStatus() {

        return ResponseEntity.ok(
                initialSetupService.isSetupCompleted()
        );
    }

    // Creates the first OWNER and FARM using the one-time setup key
    @PostMapping("/initial")
    public ResponseEntity<InitialSetupResponse> createInitialSetup(
            @RequestHeader("X-SETUP-KEY") String setupKey,
            @RequestBody InitialSetupRequest request) {

        InitialSetupResponse response =
                initialSetupService.createInitialSetup(request, setupKey);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}