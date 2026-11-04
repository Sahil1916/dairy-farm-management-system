package com.sahil.dairy.setup.service;

import com.sahil.dairy.farm.entity.Farm;
import com.sahil.dairy.farm.repository.FarmRepository;
import com.sahil.dairy.setup.InitialSetupRequest;
import com.sahil.dairy.setup.InitialSetupResponse;
import com.sahil.dairy.user.entity.User;
import com.sahil.dairy.user.enums.Roles;
import com.sahil.dairy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class InitialSetupServiceImpl implements InitialSetupService {

    private final UserRepository userRepository;
    private final FarmRepository farmRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.initial-setup.secret-key}")
    private String configuredSetupKey;

    @Override
    @Transactional
    public InitialSetupResponse createInitialSetup(
            InitialSetupRequest request,
            String setupKey) {

        // Step 1: Verify setup key and ensure setup is not completed
        validateSetup(setupKey);

        // Step 2: Validate all required fields and convert land area
        BigDecimal landArea = validateAndGetLandArea(request);

        // Step 3: Check duplicate email and phone numbers
        validateDuplicateDetails(request);

        // Step 4: Create a new Farm entity
        Farm farm = Farm.builder()
                .farmName(request.getFarmName().trim())
                .phone(request.getFarmPhone().trim())
                .email(request.getFarmEmail().trim())
                .address(request.getAddress().trim())
                .totalLandAcres(landArea)
                .build();

        // Step 5: Save Farm and generate its database ID
        Farm savedFarm = farmRepository.save(farm);

        // Step 6: Create OWNER account with encrypted password
        User owner = User.builder()
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .email(request.getEmail().trim())
                .phone(request.getPhone().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Roles.OWNER)
                .active(true)
                .farm(savedFarm)
                .build();

        // Step 7: Save OWNER account
        User savedOwner = userRepository.save(owner);

        // Step 8: Return response without exposing password
        return InitialSetupResponse.builder()
                .message("Initial setup completed successfully")
                .ownerId(savedOwner.getId())
                .ownerName(savedOwner.getFirstName() + " " + savedOwner.getLastName())
                .ownerEmail(savedOwner.getEmail())
                .farmId(savedFarm.getId())
                .farmName(savedFarm.getFarmName())
                .build();
    }

    @Override
    public boolean isSetupCompleted() {
        return userRepository.existsByRole(Roles.OWNER);
    }

    private void validateSetup(String setupKey) {

        if (configuredSetupKey == null || configuredSetupKey.isBlank()) {
            throw new IllegalStateException(
                    "Initial setup key is not configured"
            );
        }

        if (setupKey == null || !configuredSetupKey.equals(setupKey)) {
            throw new IllegalArgumentException(
                    "Invalid setup key"
            );
        }

        if (isSetupCompleted()) {
            throw new IllegalStateException(
                    "Initial setup is already completed"
            );
        }

        if (farmRepository.count() > 0) {
            throw new IllegalStateException(
                    "Farm already exists. Initial setup cannot continue"
            );
        }
    }
    private BigDecimal validateAndGetLandArea(InitialSetupRequest request) {

        if (request.getFirstName() == null || request.getFirstName().isBlank()
                || request.getLastName() == null || request.getLastName().isBlank()
                || request.getEmail() == null || request.getEmail().isBlank()
                || request.getPhone() == null || request.getPhone().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()
                || request.getFarmName() == null || request.getFarmName().isBlank()
                || request.getFarmPhone() == null || request.getFarmPhone().isBlank()
                || request.getFarmEmail() == null || request.getFarmEmail().isBlank()
                || request.getAddress() == null || request.getAddress().isBlank()
                || request.getTotalLandAcres() == null || request.getTotalLandAcres().isBlank()) {

            throw new IllegalArgumentException("All setup fields are required");
        }

        BigDecimal landArea;

        try {
            landArea = new BigDecimal(request.getTotalLandAcres());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Invalid total land area");
        }

        if (landArea.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Land area must be greater than zero");
        }

        return landArea;
    }

    // Checks whether owner and farm contact details already exist
    private void validateDuplicateDetails(InitialSetupRequest request) {

        // Check duplicate owner email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Owner email already exists"
            );
        }

        // Check duplicate owner phone
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException(
                    "Owner phone already exists"
            );
        }

        // Check duplicate farm email
        if (farmRepository.existsByEmail(request.getFarmEmail())) {
            throw new IllegalArgumentException(
                    "Farm email already exists"
            );
        }

        // Check duplicate farm phone
        if (farmRepository.existsByPhone(request.getFarmPhone())) {
            throw new IllegalArgumentException(
                    "Farm phone already exists"
            );
        }
    }
}