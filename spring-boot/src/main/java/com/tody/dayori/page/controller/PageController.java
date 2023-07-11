package com.tody.dayori.page.controller;

import com.tody.dayori.common.dto.BaseResponse;
import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.service.DiaryService;
import com.tody.dayori.page.dto.CreatePageRequest;
import com.tody.dayori.page.dto.DeletePageRequest;
import com.tody.dayori.page.dto.SearchPageRequest;
import com.tody.dayori.page.dto.UpdatePageRequest;
import com.tody.dayori.page.service.PageService;
import com.tody.dayori.user.domain.User;
import com.tody.dayori.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// Constant Message
import static com.tody.dayori.page.constant.PageConstant.*;

@Controller
@RequestMapping("/page")
@RequiredArgsConstructor
public class PageController {

    private final DiaryService diaryService;
    private final PageService pageService;

    @PostMapping
    public ResponseEntity<BaseResponse> createPage (@RequestBody CreatePageRequest createPageRequest) {
        User user = new User();
        user.setUserSeq(2L);
        Diary diary = diaryService.getDiary(createPageRequest.getDiaryId());

        return new ResponseEntity<>(BaseResponse.from(
                true,
                CREATE_PAGE_SUCCESS_MESSAGE,
                pageService.createPage(createPageRequest, user, diary)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getPage (@RequestBody SearchPageRequest searchPageRequest) {
        return new ResponseEntity<>(BaseResponse.from(
                true,
                SEARCH_PAGE_SUCCESS_MESSAGE,
                pageService.getPage(searchPageRequest)),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<BaseResponse> updatePage (@RequestBody UpdatePageRequest updatePageRequest) {
        pageService.updatePage(updatePageRequest);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                UPDATE_PAGE_SUCCESS_MESSAGE),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> deletePage (@RequestBody DeletePageRequest deletePageRequest) {
        pageService.deletePage(deletePageRequest);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                DELETE_PAGE_SUCCESS_MESSAGE),
                HttpStatus.OK);
    }
}
