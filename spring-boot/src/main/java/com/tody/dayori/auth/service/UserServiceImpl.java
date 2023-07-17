package com.tody.dayori.auth.service;

import com.tody.dayori.auth.dto.SignUpRequest;
import com.tody.dayori.auth.entity.User;
import com.tody.dayori.auth.repository.UserRepository;
import com.tody.dayori.auth.dto.LoginRequest;
import com.tody.dayori.auth.dto.AuthResponse;
import com.tody.dayori.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUserEmail(loginRequest.getEmail()).orElseThrow(
                () -> new AppException("Unknown user", HttpStatus.NOT_FOUND)
        );
        if (passwordEncoder.matches(CharBuffer.wrap(loginRequest.getPassword()), user.getPassword())) {
            return user.toAuthResponse();
        } else {
            throw new AppException("Invalid Password", HttpStatus.BAD_REQUEST);
        }

    }

    public AuthResponse register(SignUpRequest signUpDto) {
        Optional<User> optionalUser = userRepository.findByUserEmail(signUpDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new AppException("account already exists", HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .userEmail(signUpDto.getEmail())
                .userName(signUpDto.getUserName())
                .password(passwordEncoder.encode(CharBuffer.wrap(signUpDto.getPassword()))).build();
        userRepository.save(user);
        return user.toAuthResponse();
    }
}