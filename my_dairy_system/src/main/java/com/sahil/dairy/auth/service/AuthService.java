package com.sahil.dairy.auth.service;

import com.sahil.dairy.auth.dto.RegisterRequest;
import com.sahil.dairy.auth.dto.RegisterResponse;
import com.sahil.dairy.security.dto.LoginRequest;
import com.sahil.dairy.security.dto.LoginResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}