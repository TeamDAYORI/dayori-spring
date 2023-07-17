package com.tody.dayori.diary.service;

import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.dto.CreateDiaryRequest;
import com.tody.dayori.diary.repository.DiaryRepository;
import com.tody.dayori.diary.repository.UserDiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final UserDiaryRepository userDiaryRepository;

    @PersistenceContext
    EntityManager em;


    @Transactional
    public Long create(CreateDiaryRequest props   ) {
        Diary diary = Diary.create(props.getTitle(), props.getCover(), props.getDuration(), props.getPassword());
        return diaryRepository.save(diary).getDiarySeq();
    }

    @Transactional
    public Diary getDiary(Long diaryId) {
        Optional<Diary> result = diaryRepository.findById(diaryId);
        return result.orElseThrow(NullPointerException::new);
    }
}
