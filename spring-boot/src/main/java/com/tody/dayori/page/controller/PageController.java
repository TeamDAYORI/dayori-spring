package com.tody.dayori.page.controller;

import com.tody.dayori.auth.repository.UserRepository;
import com.tody.dayori.common.dto.BaseResponse;
import com.tody.dayori.common.exception.NotFoundException;
import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.service.DiaryService;
import com.tody.dayori.page.dto.CreatePageRequest;
import com.tody.dayori.page.dto.DeletePageRequest;
import com.tody.dayori.page.dto.SearchPageRequest;
import com.tody.dayori.page.dto.UpdatePageRequest;
import com.tody.dayori.page.service.PageServiceImpl;
import com.tody.dayori.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// Constant Message
import static com.tody.dayori.page.constant.PageConstant.*;
import static com.tody.dayori.common.util.TokenUtil.*;

@Controller
@RequestMapping("/page")
@RequiredArgsConstructor
public class PageController {

    private final DiaryService diaryService;
    private final PageServiceImpl pageService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<BaseResponse> createPage (@RequestBody CreatePageRequest createPageRequest) {
        User user = userRepository.findById(getCurrentUserSeq())
                    .orElseThrow(NotFoundException::new);
        Diary diary = diaryService.getDiary(createPageRequest.getDiaryId());
        if (diary.getDiaryWriter() == user.getUserSeq())
            return new ResponseEntity<>(BaseResponse.from(
                    true,
                    CREATE_PAGE_SUCCESS_MESSAGE,
                    pageService.createPage(createPageRequest, user, diary)),
                    HttpStatus.OK);
        else
            return new ResponseEntity<>(BaseResponse.from(
                false,
                NOT_MY_TURN_MESSAGE),
                HttpStatus.BAD_REQUEST);

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
        User user = userRepository.findById(getCurrentUserSeq())
                    .orElseThrow(NotFoundException::new);
        pageService.updatePage(updatePageRequest, user.getUserSeq());
        return new ResponseEntity<>(BaseResponse.from(
                true,
                UPDATE_PAGE_SUCCESS_MESSAGE),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> deletePage (@RequestBody DeletePageRequest deletePageRequest) {
        User user = userRepository.findById(getCurrentUserSeq())
                    .orElseThrow(NotFoundException::new);
        pageService.deletePage(deletePageRequest, user.getUserSeq());
        return new ResponseEntity<>(BaseResponse.from(
                true,
                DELETE_PAGE_SUCCESS_MESSAGE),
                HttpStatus.OK);
    }
}
