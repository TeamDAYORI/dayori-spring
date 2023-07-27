package com.tody.dayori.page.repository;

import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.repository.DiaryRepository;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.page.exception.PageNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PageRepositoryTest {

    @Autowired DiaryRepository diaryRepository;
    @Autowired PageRepository pageRepository;

    @Test
    @DisplayName("페이지 생성")
    void 페이지_생성() {
        // given
        User user = new User();
        user.setUserSeq(2L);
        final Diary diary = getDiary();
        diaryRepository.save(diary);

        final Page page = getPage(user, diary);

        // when
        Page savedPage = pageRepository.save(page);

        // then
        assertEquals(page, savedPage);
        assertEquals(page.getId(), savedPage.getId());
        assertEquals(page.getTitle(), savedPage.getTitle());
    }

    @Test
    @DisplayName("페이지 수정")
    void 페이지_수정() {
        // given
        Page page1 = pageRepository.findById(5L)
                .orElseThrow(PageNotFoundException::new);
        String title = "update title";
        String content = "update content";

        // when
        page1.update(title, content);
        pageRepository.save(page1);

        // then
        assertEquals(page1.getTitle(), title);
        assertEquals(page1.getContent(), content);
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