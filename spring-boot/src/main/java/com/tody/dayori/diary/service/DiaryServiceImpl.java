package com.tody.dayori.diary.service;

import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.domain.UserDiary;
import com.tody.dayori.diary.dto.CreateDiaryRequest;
import com.tody.dayori.diary.repository.DiaryRepository;
import com.tody.dayori.diary.repository.UserDiaryRepository;
import com.tody.dayori.user.domain.User;
import com.tody.dayori.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Long create(CreateDiaryRequest request) {
        Diary diary = Diary.create(request.getTitle(), request.getCover(), request.getDuration(), request.getPassword());
        diaryRepository.save(diary);
        User user = userRepository.findById(2L).orElseThrow(EntityNotFoundException::new);
        UserDiary ud = UserDiary.create(user, diary);
        userDiaryRepository.save(ud);
        return diary.getDiarySeq();
    }

    @Transactional
    public Diary getDiary(Long diaryId) {
        Optional<Diary> result = diaryRepository.findById(diaryId);
        return result.orElseThrow(NullPointerException::new);
    }

    @Transactional
    public String getInvCode(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(EntityNotFoundException::new);
        return diary.getInvitationCode();
    }
}