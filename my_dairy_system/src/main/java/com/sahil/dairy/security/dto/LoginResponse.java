package com.sahil.dairy.security.dto;

import com.sahil.dairy.user.enums.Roles;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {

    private String token;

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private Roles role;
}