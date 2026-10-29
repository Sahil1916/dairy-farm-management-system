package com.sahil.dairy.auth.dto;

import com.sahil.dairy.user.enums.Roles;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {

    private String token;

    private Long userId;

    private String fullName;

    private String email;

    private Roles role;
}