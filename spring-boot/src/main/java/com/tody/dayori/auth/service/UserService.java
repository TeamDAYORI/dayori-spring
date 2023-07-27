package com.tody.dayori.auth.service;

import com.tody.dayori.auth.dto.SignUpRequest;
import com.tody.dayori.auth.dto.LoginRequest;
import com.tody.dayori.auth.dto.AuthResponse;

public interface UserService {

    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(SignUpRequest signUp);
}
