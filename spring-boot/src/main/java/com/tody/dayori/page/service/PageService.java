package com.tody.dayori.page.service;

import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.repository.DiaryRepository;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.page.dto.CreatePageRequest;
import com.tody.dayori.page.repository.PageRepository;
import com.tody.dayori.user.domain.User;
import com.tody.dayori.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PageService {

    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    private final PageRepository pageRepository;

    @Transactional
    public Long createPage (CreatePageRequest createPageRequest, Long userId, Long diaryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("다이어리 없음"));
        createPageRequest.setUserInfo(user);
        createPageRequest.setDiary(diary);

        Page page = createPageRequest.toEntity();
        pageRepository.save(page);

        return page.getId();
    }

}
