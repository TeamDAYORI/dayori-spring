package com.tody.dayori.auth.controller;

import com.tody.dayori.auth.dto.LoginRequest;
import com.tody.dayori.auth.dto.AuthResponse;
import com.tody.dayori.config.JwtProvider;
import com.tody.dayori.auth.dto.SignUpRequest;
import com.tody.dayori.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        log.debug("로그인 api 호출");
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(userService.register(signUpRequest));
    }
}
