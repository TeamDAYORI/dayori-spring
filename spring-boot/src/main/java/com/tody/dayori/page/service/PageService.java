package com.tody.dayori.page.service;

import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.repository.DiaryRepository;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.page.dto.CreatePageRequest;
import com.tody.dayori.page.dto.SearchPageRequest;
import com.tody.dayori.page.dto.SearchPageResponse;
import com.tody.dayori.page.exception.NotExistPageException;
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

    private final PageRepository pageRepository;

    /**
     * 페이지 생성
     * @param createPageRequest diaryId, title, content
     * @param user 유저 정보
     * @param diary 다이어리 정보
     * @return pageId
     */
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

    /**
     * 페이지 상세 조회
     * @param searchPageRequest pageId
     * @return Page: title, content, date, nickname
     */
    public SearchPageResponse getPage (SearchPageRequest searchPageRequest) {
        Page page = pageRepository.findById(searchPageRequest.getPageId())
                .orElseThrow(NotExistPageException::new);
        return SearchPageResponse.builder()
                .title(page.getTitle())
                .content(page.getContent())
                .date(page.getDate())
                .nickname(page.getUserInfo().getNickname())
                .build();
    }

}
