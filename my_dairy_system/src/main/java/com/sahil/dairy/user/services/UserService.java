package com.sahil.dairy.user.services;

import com.sahil.dairy.user.dto.UserRequestDto;
import com.sahil.dairy.user.dto.UserResponseDto;
import com.sahil.dairy.user.enums.Roles;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto request);

    UserResponseDto updateUser(Long id, UserRequestDto request);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    ResponseEntity<String> activateUser(Long id);

    ResponseEntity<String> deactivateUser(Long id);

    List<UserResponseDto> getUsersByRole(Roles role);

    List<UserResponseDto> getUsersByFarm(Long farmId);

    List<UserResponseDto> getActiveUsers();

    List<UserResponseDto> getInactiveUsers();
}