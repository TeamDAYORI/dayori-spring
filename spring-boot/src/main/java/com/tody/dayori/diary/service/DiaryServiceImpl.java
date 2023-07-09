package com.tody.dayori.diary.service;

import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.domain.UserDiary;
import com.tody.dayori.diary.dto.CreateDiaryRequest;
import com.tody.dayori.diary.repository.DiaryRepository;
import com.tody.dayori.diary.repository.UserDiaryRepository;
import com.tody.dayori.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
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
    public Long create(CreateDiaryRequest request, User user) {
        Diary diary = Diary.create(request.getTitle(), request.getCover(), request.getDuration(), request.getPassword());

        Long dSeq = diaryRepository.save(diary).getDiarySeq();
        Diary diary1 = diaryRepository.findById(dSeq)
                .orElseThrow(EntityNotFoundException::new);
//        UserDiary userDiary = UserDiary.create(user, diary1);
//        userDiaryRepository.save(userDiary);
        userDiaryRepository.save(UserDiary.create(user, diary1));
        return dSeq;
    }

    @Transactional
    public Diary getDiary(Long diaryId) {
        Optional<Diary> result = diaryRepository.findById(diaryId);
        return result.orElseThrow(NullPointerException::new);
    }
}
