package com.sahil.dairy.user.controller;

import com.sahil.dairy.user.dto.UserRequestDto;
import com.sahil.dairy.user.dto.UserResponseDto;
import com.sahil.dairy.user.enums.Roles;
import com.sahil.dairy.user.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto createUser(
            @Valid @RequestBody UserRequestDto request) {

        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto request) {

        return userService.updateUser(id, request);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(
            @PathVariable Long id) {

        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {

        return userService.getAllUsers();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<String> activateUser(
            @PathVariable Long id) {

        return userService.activateUser(id);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateUser(
            @PathVariable Long id) {

        return userService.deactivateUser(id);
    }

    @GetMapping("/role")
    public List<UserResponseDto> getUsersByRole(
            @RequestParam Roles role) {

        return userService.getUsersByRole(role);
    }

    @GetMapping("/farm/{farmId}")
    public List<UserResponseDto> getUsersByFarm(
            @PathVariable Long farmId) {

        return userService.getUsersByFarm(farmId);
    }

    @GetMapping("/active")
    public List<UserResponseDto> getActiveUsers() {

        return userService.getActiveUsers();
    }

    @GetMapping("/inactive")
    public List<UserResponseDto> getInactiveUsers() {

        return userService.getInactiveUsers();
    }
}