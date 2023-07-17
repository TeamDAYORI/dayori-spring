package com.tody.dayori.auth.controller;

import com.tody.dayori.auth.dto.LoginRequest;
import com.tody.dayori.auth.dto.AuthResponse;
import com.tody.dayori.auth.service.JwtProvider;
import com.tody.dayori.auth.dto.SignUpRequest;
import com.tody.dayori.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = userService.login(loginRequest);
        authResponse.setAccessToken(jwtProvider.createToken(authResponse.getSeq()));
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody SignUpRequest signUpDto) {
        AuthResponse authResponse = userService.register(signUpDto);
        authResponse.setAccessToken(jwtProvider.createToken(authResponse.getSeq()));
        return ResponseEntity.created(URI.create("/users/" + authResponse.getSeq()))
                .body(authResponse);
    }
}
