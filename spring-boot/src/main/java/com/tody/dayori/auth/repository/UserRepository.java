package com.tody.dayori.auth.repository;

import com.tody.dayori.auth.entity.User;
import com.tody.dayori.diary.domain.UserDiary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);
    User findByUserSeq(Long userSeq);
    List<User> findByNickNameStartsWith(String userName);
}
