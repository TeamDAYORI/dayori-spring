package com.tody.dayori.diary.repository;

import com.tody.dayori.auth.entity.User;
import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.domain.UserDiary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface UserDiaryRepository extends JpaRepository<UserDiary, Long> {
    UserDiary findByUserAndDiary(User user, Diary diary);
    List<UserDiary> findByUser(User user);
    List<UserDiary> findAllByDiaryOrderByInsDate(Diary diary);
}
