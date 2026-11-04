package com.sahil.dairy.setup;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InitialSetupResponse {

    private String message;
    private Long ownerId;
    private String ownerName;
    private String ownerEmail;
    private Long farmId;
    private String farmName;
}