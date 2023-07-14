package com.tody.dayori.diary.service;

import com.tody.dayori.common.exception.DuplicateException;
import com.tody.dayori.common.exception.NotFoundException;
import com.tody.dayori.common.exception.NotMatchException;
import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.domain.UserDiary;
import com.tody.dayori.diary.dto.CreateDiaryRequest;
import com.tody.dayori.diary.dto.DiaryResponse;
import com.tody.dayori.diary.dto.JoinDiaryRequest;
import com.tody.dayori.diary.dto.UpdateDiaryRequest;
import com.tody.dayori.diary.repository.DiaryRepository;
import com.tody.dayori.diary.repository.UserDiaryRepository;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.user.domain.User;
import com.tody.dayori.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
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
        Long id = diary.getDiarySeq();
        byte[] idBytes = id.toString().getBytes(StandardCharsets.UTF_8);
        byte[] base64Bytes = Base64.encodeBase64(idBytes);
        String base64String = new String(base64Bytes, StandardCharsets.UTF_8).trim();
        diary.addInvCode(base64String);
        return id;
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

    @Transactional
    public void joinDiary(Long diaryId, JoinDiaryRequest request) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(5L).orElseThrow(EntityNotFoundException::new);
        UserDiary userDiary = userDiaryRepository.findByUserAndDiary(user, diary);
        if (userDiary != null) {
            throw new DuplicateException(String.format("%s는 이미 가입된 다이어리입니다.", diary.getDiaryTitle()));
        } else {
            if (request.getPassword().equals(diary.getDiaryPassword())){
                UserDiary ud = UserDiary.create(user, diary);
                userDiaryRepository.save(ud);
            } else {
                throw new NotMatchException(NotMatchException.PASSWORD_NOT_MATCH);
            }
        }
    }


    @Transactional
    public List<DiaryResponse> getDiaryList() {
        User user = userRepository.findById(2L).orElseThrow(EntityNotFoundException::new);
        List<DiaryResponse> diaries = userDiaryRepository.findByUser(user).stream()
                .map(DiaryResponse::response)
                .collect(Collectors.toList());
        return diaries;
    }

    @Transactional
    public void update(Long diaryId, UpdateDiaryRequest request) {
        User user = userRepository.findById(2L).orElseThrow(EntityNotFoundException::new);
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(EntityNotFoundException::new);
        UserDiary ud = userDiaryRepository.findByUserAndDiary(user, diary);
        if (ud != null) {
            ud.update(request.getTitle(), request.getCover());
        }
    }
}