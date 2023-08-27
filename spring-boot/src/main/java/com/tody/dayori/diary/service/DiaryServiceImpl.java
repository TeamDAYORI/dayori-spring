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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
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
    public Long create(CreateDiaryRequest request, User user) {
        Diary diary = Diary.create(user.getUserSeq(), request.getTitle(), request.getCover(), request.getDuration(), request.getPassword());
        diaryRepository.save(diary);
        UserDiary ud = UserDiary.create(user, diary);
        userDiaryRepository.save(ud);
        Long DiaryId = diary.getDiarySeq();
        // 초대 부분
        request.getMembers().stream()
                .forEach(memberSeq -> {
                    User member = userRepository.findById(memberSeq).orElseThrow(EntityNotFoundException::new);
                    UserDiary ud2 = UserDiary.invited(member, diary);
                    userDiaryRepository.save(ud2);
                });

        return DiaryId;
    }

    @Transactional
    public Diary getDiary(Long diaryId) {
        Optional<Diary> result = diaryRepository.findById(diaryId);
        return result.orElseThrow(NullPointerException::new);
    }

    @Transactional
    public void joinAcceptDiary(Long diaryId, JoinDiaryRequest request, User user) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(EntityNotFoundException::new);
        UserDiary userDiary = userDiaryRepository.findByUserAndDiary(user, diary);
        // 초대를 받지 않아 userDiary Entity에서 해당정보 찾을 수 없음.
        if (userDiary == null) {
            throw new NotMatchException(String.format("%s에 가입할 권한이 없습니다.", diary.getDiaryTitle()));
        } else if (userDiary.getIsJoined() == 1) { // 혹시 이미 가입된 다이어리라면,
            throw new DuplicateException(String.format("%s는 이미 가입된 다이어리 입니다.", diary.getDiaryTitle()));
        } else { // 초대받음
            // 올바른 비밀번호
            if (request.getPassword().equals(diary.getDiaryPassword())){
                userDiary.accept();
                userDiaryRepository.save(userDiary);
            } else { // 틀린 비밀번호
                throw new NotMatchException(NotMatchException.PASSWORD_NOT_MATCH);
            }
        }
    }

    @Transactional
    public void joinRefuseDiary(Long diaryId, User user) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(EntityNotFoundException::new);
        UserDiary userDiary = userDiaryRepository.findByUserAndDiary(user, diary);
        // 초대를 받지 않아 userDiary Entity에서 해당정보 찾을 수 없음.
        if (userDiary == null) {
            throw new NotMatchException(String.format("%s에 가입할 권한이 없습니다.", diary.getDiaryTitle()));
        } else if (userDiary.getIsJoined() == 1) { // 혹시 이미 가입된 다이어리라면,
            throw new DuplicateException(String.format("%s는 이미 가입된 다이어리 입니다.", diary.getDiaryTitle()));
        } else{
            userDiaryRepository.delete(userDiary);
        }
    }


    @Transactional
    public List<DiaryResponse> getDiaryList(User user) {
        List<DiaryResponse> diaries = userDiaryRepository.findByUser(user).stream()
                .map(DiaryResponse::response)
                .collect(Collectors.toList());
        // isJoined가 0인 경우 가장 우선순위, myTurn이 1인 경우 다음 우선순위
        Collections.sort(diaries, Comparator.comparingInt(DiaryResponse::getMyTurn)
                .reversed()
                .thenComparingInt(DiaryResponse::getIsJoined));

        return diaries;
    }

    @Transactional
    public void update(Long diaryId, UpdateDiaryRequest request, User user) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(EntityNotFoundException::new);
        UserDiary ud = userDiaryRepository.findByUserAndDiary(user, diary);
        if (ud != null) {
            ud.update(request.getTitle(), request.getCover());
        }
    }

    @Transactional
    public void withdraw(Long diaryId, User user) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(EntityNotFoundException::new);
        UserDiary ud = userDiaryRepository.findByUserAndDiary(user, diary);
        // 이 부분 분기처리 추가
        if (ud != null && ud.getIsJoined() == 1) {
            userDiaryRepository.delete(ud);
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
//    @Scheduled(cron = "0 * * * * ?") // 매분 0초에 실행
    @Transactional
    public void passDiaryWriter(){

        List<Diary> diaries = diaryRepository.findAll();

        for (Diary diary : diaries) {
            int duration = diary.getDiaryDuration();
            Long Writer = diary.getDiaryWriter();
            Boolean nextAble = diary.getNextAble();
            User nowUser = userRepository.findById(Writer).orElseThrow(EntityNotFoundException::new);

            if (duration > 0) {
                Long next = WhoIsNext(nowUser, diary);
                User nextUser = userRepository.findByUserSeq(next);
                UserDiary nextUd = userDiaryRepository.findByUserAndDiary(nextUser, diary);
                UserDiary known = userDiaryRepository.findByUserAndDiary(nowUser, diary);
                if (duration == 1 || nextAble) {
                    // duration 이 1인 경우 항상 실행 || duration 상관 없이 nextAble 이 true 면 차례 넘김
                    known.revoke();
                    nextUd.grant();
                    diary.updateWriter(next);
                } else {
                    LocalDateTime createDiaryDate = diary.getDiaryCreateAt();
                    LocalDateTime currentDate = LocalDateTime.now();
                    Long daysPassed = ChronoUnit.DAYS.between(createDiaryDate, currentDate);
//                    Long daysPassed = ChronoUnit.MINUTES.between(createDiaryDate, currentDate);
                    if (daysPassed % duration == 0) {
                        known.revoke();
                        nextUd.grant();
                        diary.updateWriter(next);
                    }
                }
            }
        }
        diaryRepository.saveAll(diaries);

    }

    public Long WhoIsNext(User user, Diary diary) {
        UserDiary known = userDiaryRepository.findByUserAndDiary(user, diary);
        List<UserDiary> userDiaries = userDiaryRepository.findAllByDiaryAndIsJoinedOrderByInsDate(diary, 1);

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

    // 친구 초대할 때 사용하는 유저 검색 기능
    // 초대하려는 다이어리에 이미 가입된 멤버는 검색대상에서 제외하거나 이미 가입된 멤버라고 보여주는 표시가 필요할 듯,,,
    @Cacheable
    public List<SearchUserResponse> searchUserByName(String userName, SearchUserRequest request, User me) {
        if (userName.isEmpty()) {
            return Collections.emptyList(); // 비어있을 경우 빈 리스트 반환
        }
        List<SearchUserResponse> users = userRepository.findByNickNameContaining(userName)
                .stream()
                .filter(user -> !user.equals(me) && !request.getJoinedUsers().contains(user.getUserSeq()))
                .map(SearchUserResponse::response)
                .collect(Collectors.toList());
        return users;
    }



}