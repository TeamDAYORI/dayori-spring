package com.tody.dayori.page.service;

import com.tody.dayori.auth.entity.User;
import com.tody.dayori.auth.repository.UserRepository;
import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.dto.JoinDiaryRequest;
import com.tody.dayori.diary.repository.DiaryRepository;
import com.tody.dayori.diary.service.DiaryServiceImpl;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.page.dto.DeletePageRequest;
import com.tody.dayori.page.dto.UpdatePageRequest;
import com.tody.dayori.page.exception.PageNotFoundException;
import com.tody.dayori.page.exception.PageUnauthorizedException;
import com.tody.dayori.page.repository.PageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PageServiceTest {

    @Autowired private PageServiceImpl pageService;
    @Autowired private DiaryServiceImpl diaryService;
    @Autowired private PageRepository pageRepository;
    @Autowired private DiaryRepository diaryRepository;
    @Autowired private UserRepository userRepository;

    @Test
    @DisplayName("페이지 조회 : 존재하지 않는 페이지")
    void 존재하지않는페이지_조회 () {
        // given
        Long pageId = 99L;

        // then
        assertThrows(PageNotFoundException.class, () -> pageService.findPageById(pageId));
    }

    // 페이지 생성 권한 [순서]
//    @Test
//    @DisplayName("페이지 작성 : 순서 아닌 사용자")
//    void 순서아닌페이지_작성 () {
//        final User user1 = User.builder()
//                .userName("test_user1")
//                .userEmail("test_email1@naver.com")
//                .password("test_password")
//                .userImgUrl("test_url")
//                .build();
//        final User user2 = User.builder()
//                .userName("test_user2")
//                .userEmail("test_email2@naver.com")
//                .password("test_password")
//                .userImgUrl("test_url")
//                .build();
//        userRepository.save(user1);
//        userRepository.save(user2);
//
//        final Diary diary = getDiary(user1); // user1 이 생성한 다이어리
//        final Page page = getPage(user1, diary); // user1 이 작성한 페이지
//        diaryRepository.save(diary);
//
//        // user2 다이어리 가입
//        JoinDiaryRequest joinDiaryRequest = new JoinDiaryRequest("diary password");
//        diaryService.joinDiary(diary.getDiarySeq(), joinDiaryRequest);
//
//    }


    @Test
    @DisplayName("페이지 수정 : 권한 없는 사용자")
    void 권한없는페이지_수정 () {
        // given
        final User user1 = User.builder()
                .userName("test_user1")
                .userEmail("test_email1@naver.com")
                .password("test_password")
                .userImgUrl("test_url")
                .build();
        final User user2 = User.builder()
                .userName("test_user2")
                .userEmail("test_email2@naver.com")
                .password("test_password")
                .userImgUrl("test_url")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        final Diary diary = getDiary(user1);
        final Page page = getPage(user1, diary); // user1 이 작성한 페이지
        diaryRepository.save(diary);
        Page savedPage = pageRepository.save(page);

        // when
        UpdatePageRequest updatePageRequest =
                UpdatePageRequest.builder()
                        .pageId(savedPage.getId())
                        .title("page title update")
                        .content("page content update")
                        .build();
        // then
        assertThrows(PageUnauthorizedException.class,
                () ->  pageService.updatePage(updatePageRequest, user2.getUserSeq()));
    }

    @Test
    @DisplayName("페이지 삭제 : 권한 없는 사용자")
    void 권한없는페이지_삭제 () {
        // given
        final User user1 = User.builder()
                .userName("test_user1")
                .userEmail("test_email1@naver.com")
                .password("test_password")
                .userImgUrl("test_url")
                .build();
        final User user2 = User.builder()
                .userName("test_user2")
                .userEmail("test_email2@naver.com")
                .password("test_password")
                .userImgUrl("test_url")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        final Diary diary = getDiary(user1);
        final Page page = getPage(user1, diary); // user1 이 작성한 페이지
        diaryRepository.save(diary);
        Page savedPage = pageRepository.save(page);

        // when
        DeletePageRequest deletePageRequest = DeletePageRequest.builder()
                .pageId(savedPage.getId())
                .build();

        // then
        assertThrows(PageUnauthorizedException.class,
                () ->  pageService.deletePage(deletePageRequest, user2.getUserSeq()));
    }



    private Diary getDiary (User user) {
        return Diary.builder()
                .diaryTitle("diary title")
                .diaryCover("diary cover")
                .diaryDuration(100)
                .diaryPassword("diary password")
                .diaryWithdraw(false)
                .invitationCode("")
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