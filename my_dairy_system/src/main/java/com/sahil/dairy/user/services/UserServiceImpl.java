package com.sahil.dairy.user.services;

import com.sahil.dairy.exception.DuplicateResourceException;
import com.sahil.dairy.exception.ResourceNotFoundException;
import com.sahil.dairy.farm.entity.Farm;
import com.sahil.dairy.farm.repository.FarmRepository;
import com.sahil.dairy.user.dto.UserRequestDto;
import com.sahil.dairy.user.dto.UserResponseDto;
import com.sahil.dairy.user.entity.User;
import com.sahil.dairy.user.enums.Roles;
import com.sahil.dairy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final FarmRepository farmRepository;

    private UserResponseDto mapToResponse(User user) {

        return UserResponseDto.builder()
                .id(user.getId())
                .farmId(user.getFarm() != null ? user.getFarm().getId() : null)
                .farmName(user.getFarm() != null ? user.getFarm().getFarmName() : null)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
    @Override
    public UserResponseDto createUser(UserRequestDto request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Owner not found"));

        Farm farm = owner.getFarm();

        if (farm == null) {
            throw new ResourceNotFoundException(
                    "Owner has not created a farm yet."
            );
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone already exists");
        }

        User user = User.builder()
                .farm(farm)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .active(true)
                .build();

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException("Email already exists");
        }

        if (!user.getPhone().equals(request.getPhone())
                && userRepository.existsByPhone(request.getPhone())) {

            throw new DuplicateResourceException("Phone already exists");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    @Override
    public UserResponseDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ResponseEntity<String> activateUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setActive(true);

        userRepository.save(user);

        return ResponseEntity.ok("User activated successfully.");
    }

    @Override
    public ResponseEntity<String> deactivateUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setActive(false);

        userRepository.save(user);

        return ResponseEntity.ok("User deactivated successfully.");
    }

    @Override
    public List<UserResponseDto> getUsersByRole(Roles role) {

        return userRepository.findByRole(role)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<UserResponseDto> getUsersByFarm(Long farmId) {

        return userRepository.findByFarmId(farmId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<UserResponseDto> getActiveUsers() {

        return userRepository.findByActive(true)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<UserResponseDto> getInactiveUsers() {

        return userRepository.findByActive(false)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}
