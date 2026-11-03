package com.sahil.dairy.auth.service;

import com.sahil.dairy.security.dto.LoginRequest;
import com.sahil.dairy.security.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}