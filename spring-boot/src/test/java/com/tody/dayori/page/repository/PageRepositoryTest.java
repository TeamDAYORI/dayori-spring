package com.tody.dayori.page.repository;

import com.tody.dayori.auth.entity.User;
import com.tody.dayori.auth.repository.UserRepository;
import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.repository.DiaryRepository;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.page.exception.PageNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PageRepositoryTest {

    @Autowired private UserRepository userRepository;
    @Autowired private DiaryRepository diaryRepository;
    @Autowired private PageRepository pageRepository;

    @Test
    @DisplayName("페이지 생성")
    void 페이지_생성() {
        // given
        final User user = User.builder()
                .userName("test_user")
                .userEmail("test_email@naver.com")
                .build();
        final Diary diary = getDiary(user);
        final Page page = getPage(user, diary);
        userRepository.save(user);
        diaryRepository.save(diary);

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
        final User user = User.builder()
                .userName("test_user")
                .userEmail("test_email@naver.com")
                .build();
        final Diary diary = getDiary(user);
        final Page page = getPage(user, diary);
        userRepository.save(user);
        diaryRepository.save(diary);
        Page savedPage = pageRepository.save(page);

        String title = "update title";
        String content = "update content";

        // when
        savedPage.update(title, content);
        pageRepository.save(savedPage);

        // then
        assertEquals(savedPage.getTitle(), title);
        assertEquals(savedPage.getContent(), content);
    }

    private Diary getDiary (User user) {
        return Diary.builder()
                .diaryTitle("diary title")
                .diaryCover("diary cover")
                .diaryDuration(100)
                .diaryPassword("diary password")
                .diaryWithdraw(false)
                .diaryWriter(user.getUserSeq())
                .nextAble(false)
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