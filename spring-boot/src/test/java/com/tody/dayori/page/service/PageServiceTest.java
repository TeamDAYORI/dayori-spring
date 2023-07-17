package com.tody.dayori.page.service;

import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.repository.DiaryRepository;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.page.dto.DeletePageRequest;
import com.tody.dayori.page.dto.UpdatePageRequest;
import com.tody.dayori.page.exception.PageNotFoundException;
import com.tody.dayori.page.exception.PageUnauthorizedException;
import com.tody.dayori.page.repository.PageRepository;
import com.tody.dayori.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class PageServiceTest {

    @InjectMocks private PageService pageService;
    @Mock private PageRepository pageRepository;
    @Mock private DiaryRepository diaryRepository;

    @Test
    @DisplayName("페이지 조회 : 존재하지 않는 페이지")
    void 존재하지않는페이지_조회 () {
        // given
        Long pageId = 99L;

        // when

        // then
        assertThrows(PageNotFoundException.class, () -> pageService.findPageById(pageId));
    }

    @Test
    @DisplayName("페이지 삭제 : 존재하지 않는 페이지")
    void 존재하지않는페이지_삭제 () {
        // given
        Long pageId = 99L;
        DeletePageRequest deletePageRequest =
                DeletePageRequest.builder()
                                .pageId(pageId)
                                .build();
        // when

        // then
        assertThrows(PageNotFoundException.class,
                () -> pageService.deletePage(deletePageRequest));
    }

    // 페이지 생성 권한 [순서]
    // 페이지 수정 권한

    @Test
    @DisplayName("페이지 수정 : 권한 없는 사용자")
    void 권한없는페이지_수정 () {
        // given
        User user = new User();
        user.setUserSeq(2L);

        Diary diary = getDiary();
        diaryRepository.save(diary);
        Page page = getPage(user, diary);
        Page savedPage = pageRepository.save(page);

        Long userSeq = 99L;
        UpdatePageRequest updatePageRequest =
                UpdatePageRequest.builder()
                        .pageId(savedPage.getId())
                        .title("page title update")
                        .content("page content update")
                        .build();

        // when

        // then
        assertThrows(PageUnauthorizedException.class,
                () ->  pageService.updatePage(updatePageRequest, userSeq));

    }

    private Diary getDiary () {
        return Diary.builder()
                .diaryTitle("diary title")
                .diaryCover("diary cover")
                .diaryDuration(100)
                .diaryPassword("diary password")
                .diaryWithdraw(false)
                .build();
    }

    private Page getPage (User user, Diary diary) {
        return Page.builder()
                .title("page title")
                .content("page content")
                .userInfo(user)
                .diary(diary)
                .build();
    }
}