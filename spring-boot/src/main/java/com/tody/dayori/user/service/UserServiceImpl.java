package com.tody.dayori.user.service;

import com.tody.dayori.user.domain.User;
import com.tody.dayori.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public User getUser(User user) {
        Optional<User> result = userRepository.findById(user.getUserSeq());
        return result.orElseThrow(NullPointerException::new);
    }

}
