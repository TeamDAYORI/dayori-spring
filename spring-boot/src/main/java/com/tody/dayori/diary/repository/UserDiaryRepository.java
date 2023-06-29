package com.tody.dayori.diary.repository;

import com.tody.dayori.diary.domain.UserDiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDiaryRepository extends JpaRepository<UserDiary, Long> {
}
