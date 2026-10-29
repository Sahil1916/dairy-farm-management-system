package com.sahil.dairy.auth.dto;

import com.sahil.dairy.user.enums.Roles;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {

    private String token;

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private Roles role;
}