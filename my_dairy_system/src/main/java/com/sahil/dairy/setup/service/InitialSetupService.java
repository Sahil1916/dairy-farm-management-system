package com.sahil.dairy.setup.service;

import com.sahil.dairy.setup.InitialSetupRequest;
import com.sahil.dairy.setup.InitialSetupResponse;

public interface InitialSetupService {

    InitialSetupResponse createInitialSetup(
            InitialSetupRequest request,
            String setupKey
    );

    boolean isSetupCompleted();
}