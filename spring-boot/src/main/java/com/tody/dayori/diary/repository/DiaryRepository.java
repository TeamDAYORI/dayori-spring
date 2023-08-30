package com.tody.dayori.diary.repository;

import com.tody.dayori.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Diary findByDiarySeq(Long diarySeq);
}
