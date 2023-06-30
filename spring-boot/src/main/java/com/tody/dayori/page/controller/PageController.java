package com.tody.dayori.page.controller;

import com.tody.dayori.common.BaseResponse;
import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.service.DiaryService;
import com.tody.dayori.diary.service.DiaryServiceImpl;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.page.dto.CreatePageRequest;
import com.tody.dayori.page.service.PageService;
import com.tody.dayori.user.domain.User;
import com.tody.dayori.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

// Constant Message
import static com.tody.dayori.page.constant.PageConstant.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;
    private final DiaryService diaryService;
    private final PageService pageService;

    @PostMapping("/page")
    public ResponseEntity<BaseResponse> createPage (@RequestBody CreatePageRequest createPageRequest) {
        User user = new User();
        user.setUserSeq(2);
        Diary diary = diaryService.getDiary(createPageRequest.getDiaryId());

        return new ResponseEntity<>(BaseResponse.from(
                true,
                CREATE_POST_SUCCESS_MESSAGE,
                pageService.createPage(createPageRequest, user, diary)),
                HttpStatus.OK);
    }
}
