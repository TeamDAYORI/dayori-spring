package com.tody.dayori.diary.controller;

import com.tody.dayori.common.dto.BaseResponse;
import com.tody.dayori.diary.domain.UserDiary;
import com.tody.dayori.diary.dto.CreateDiaryRequest;
import com.tody.dayori.diary.service.DiaryServiceImpl;
import com.tody.dayori.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.tody.dayori.page.constant.PageConstant.CREATE_PAGE_SUCCESS_MESSAGE;

@RestController
//@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryServiceImpl diaryService;

    @PostMapping("/diary")
    public ResponseEntity<BaseResponse> createDiary(@RequestBody CreateDiaryRequest request){
        User user = new User();
        user.setUserSeq(2L);
        Long diaryId = diaryService.create(request, user);
        Map<String, Long> response = new HashMap<>();
        response.put("diaryId", diaryId);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                CREATE_PAGE_SUCCESS_MESSAGE,
                diaryId),
                HttpStatus.OK);
    }


}
