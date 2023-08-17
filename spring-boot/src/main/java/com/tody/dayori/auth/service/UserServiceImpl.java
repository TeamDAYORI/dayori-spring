package com.tody.dayori.auth.service;

import com.tody.dayori.auth.dto.SignUpRequest;
import com.tody.dayori.auth.entity.Role;
import com.tody.dayori.auth.entity.User;
import com.tody.dayori.auth.repository.UserRepository;
import com.tody.dayori.auth.dto.LoginRequest;
import com.tody.dayori.auth.dto.AuthResponse;
import com.tody.dayori.config.JwtProvider;
import com.tody.dayori.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest loginRequest) {
        System.out.println("여기서");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        System.out.println("터짐");
        User user = userRepository.findByUserEmail(loginRequest.getEmail())
                .orElseThrow();
        String token = jwtProvider.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(SignUpRequest signUpRequest) {
        User user = User.builder().
            userName(signUpRequest.getUserName())
                .userEmail(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token = jwtProvider.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}