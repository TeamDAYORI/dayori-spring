package com.tody.dayori.page.controller;

import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.service.DiaryService;
import com.tody.dayori.diary.service.DiaryServiceImpl;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.page.dto.CreatePageRequest;
import com.tody.dayori.page.service.PageService;
import com.tody.dayori.user.domain.User;
import com.tody.dayori.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;
    private final DiaryService diaryService;
    private final PageService pageService;

    @PostMapping("/page/{diaryId}")
    public ResponseEntity<?> createPage (@PathVariable("diaryId") Long diaryId,
                                         @RequestBody CreatePageRequest createPageRequest) {
        User user = new User();
        user.setUserSeq(2);

        Diary diary = diaryService.getDiary(diaryId);
        Long pageId = pageService.createPage(createPageRequest, user.getUserSeq(), diaryId);
        Map<String, Long> response = new HashMap<>();
        response.put("pageId", pageId);
        return ResponseEntity.ok(response);
    }
}
