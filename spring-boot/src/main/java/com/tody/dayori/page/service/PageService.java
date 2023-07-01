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

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PageService {

    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    private final PageRepository pageRepository;

    @Transactional
    public Long createPage (CreatePageRequest createPageRequest, User user, Diary diary) {
        Page page = Page.builder()
                    .title(createPageRequest.getTitle())
                    .content(createPageRequest.getContent())
                    .date(LocalDateTime.now())
                    .userInfo(user)
                    .diary(diary)
                    .build();
        pageRepository.save(page);

        return page.getId();
    }

}
