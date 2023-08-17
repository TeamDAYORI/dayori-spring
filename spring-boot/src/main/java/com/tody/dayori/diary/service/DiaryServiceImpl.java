package com.tody.dayori.diary.service;

import com.tody.dayori.auth.entity.User;
import com.tody.dayori.auth.repository.UserRepository;
import com.tody.dayori.common.exception.DuplicateException;
import com.tody.dayori.common.exception.NotMatchException;
import com.tody.dayori.common.util.TokenUtil;
import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.domain.UserDiary;
import com.tody.dayori.diary.dto.*;
import com.tody.dayori.diary.repository.DiaryRepository;
import com.tody.dayori.diary.repository.UserDiaryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;


@Service
@CacheConfig(cacheNames = "userSearchCache")
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
        Long userSeq = TokenUtil.getCurrentUserSeq();
        Diary diary = Diary.create(userSeq, request.getTitle(), request.getCover(), request.getDuration(), request.getPassword());
        diaryRepository.save(diary);
        com.tody.dayori.auth.entity.User user = userRepository.findById(userSeq).orElseThrow(EntityNotFoundException::new);
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
        Long userSeq = TokenUtil.getCurrentUserSeq();
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(EntityNotFoundException::new);
        com.tody.dayori.auth.entity.User user = userRepository.findById(userSeq).orElseThrow(EntityNotFoundException::new);
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
        Long userSeq = TokenUtil.getCurrentUserSeq();
        User user = userRepository.findById(userSeq).orElseThrow(EntityNotFoundException::new);
        List<DiaryResponse> diaries = userDiaryRepository.findByUser(user).stream()
                .map(DiaryResponse::response)
                .collect(Collectors.toList());
        return diaries;
    }

    @Transactional
    public void update(Long diaryId, UpdateDiaryRequest request) {
        Long userSeq = TokenUtil.getCurrentUserSeq();
        User user = userRepository.findById(userSeq).orElseThrow(EntityNotFoundException::new);
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(EntityNotFoundException::new);
        UserDiary ud = userDiaryRepository.findByUserAndDiary(user, diary);
        if (ud != null) {
            ud.update(request.getTitle(), request.getCover());
        }
    }

//    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    @Scheduled(cron = "0 * * * * ?") // 매분 0초에 실행
    @Transactional
    public void passDiaryWriter(){

        List<Diary> diaries = diaryRepository.findAll();

        for (Diary diary : diaries) {
            int duration = diary.getDiaryDuration();
            Long Writer = diary.getDiaryWriter();
            Boolean nextAble = diary.getNextAble();
            User nowUser = userRepository.findById(Writer).orElseThrow(EntityNotFoundException::new);

            if (duration > 0) {
                if (duration == 1 || nextAble) {
                    // duration 이 1인 경우 항상 실행 || duration 상관 없이 nextAble 이 true 면 차례 넘김
                    diary.updateWriter(WhoIsNext(nowUser, diary));
                } else {
                    LocalDateTime createDiaryDate = diary.getDiaryCreateAt();
                    LocalDateTime currentDate = LocalDateTime.now();
//                  Long daysPassed = ChronoUnit.DAYS.between(createDiaryDate, currentDate);
                    Long daysPassed = ChronoUnit.MINUTES.between(createDiaryDate, currentDate);

                    if (daysPassed % duration == 0) {
                        diary.updateWriter(WhoIsNext(nowUser, diary));
                    }
                }
            }
        }
        diaryRepository.saveAll(diaries);

    }

    public Long WhoIsNext(User user, Diary diary) {
        UserDiary known = userDiaryRepository.findByUserAndDiary(user, diary);
        List<UserDiary> userDiaries = userDiaryRepository.findAllByDiaryOrderByInsDate(diary);

        if (userDiaries.size() == 1) { // 다이어리 가입 멤버가 한명이면 바로 본인 리턴
            return user.getUserSeq();
        }

        boolean foundKnown = false;
        Long nextUserSeq = 0L;

        for (UserDiary userDiary : userDiaries) {
            if (!foundKnown && userDiary.getUser().equals(known.getUser())) {
                foundKnown = true;
                continue;
            }
            if (foundKnown) {
                nextUserSeq = userDiary.getUser().getUserSeq();
                break;
            }
        }

        if (nextUserSeq != 0L) {
            return nextUserSeq;
        } else {
            // 팀의 마지막 멤버인 경우, 팀에 가장 처음 가입한 유저 지정
            UserDiary first = userDiaries.get(0);
            return first.getUser().getUserSeq();
        }
    }

    @Cacheable
    public List<SearchUserResponse> searchUserByName(String userName) {
        List<SearchUserResponse> users = userRepository.findByNickNameStartsWith(userName)
                .stream()
                .map(SearchUserResponse::response)
                .collect(Collectors.toList());
        return users;
    }

}