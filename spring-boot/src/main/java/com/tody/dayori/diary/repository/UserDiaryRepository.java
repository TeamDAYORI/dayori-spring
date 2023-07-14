package com.tody.dayori.diary.repository;

import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.domain.UserDiary;
import com.tody.dayori.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDiaryRepository extends JpaRepository<UserDiary, Long> {
    UserDiary findByUserAndDiary(User user, Diary diary);
}
