package com.sahil.dairy.setup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitialSetupRequest {

    // Owner Details
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;

    // Farm Details
    private String farmName;
    private String farmPhone;
    private String farmEmail;
    private String address;
    private String totalLandAcres;
}