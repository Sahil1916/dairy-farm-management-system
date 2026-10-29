package com.sahil.dairy.user.dto;

import com.sahil.dairy.user.enums.Roles;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private Long id;

    private Long farmId;

    private String farmName;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private Roles role;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}