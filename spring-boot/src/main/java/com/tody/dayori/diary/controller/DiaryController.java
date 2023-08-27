package com.tody.dayori.diary.controller;

import com.tody.dayori.auth.entity.User;
import com.tody.dayori.common.dto.BaseResponse;
import com.tody.dayori.diary.dto.*;
import com.tody.dayori.diary.service.DiaryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tody.dayori.diary.constant.DiaryConstant.*;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryServiceImpl diaryService;

    @PostMapping("")
    public ResponseEntity<BaseResponse> createDiary(@RequestBody CreateDiaryRequest request, @AuthenticationPrincipal User user){
        Long diaryId = diaryService.create(request, user);
        Map<String, Long> response = new HashMap<>();
        response.put("diaryId", diaryId);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                CREATE_DIARY_SUCCESS_MESSAGE,
                diaryId),
                HttpStatus.OK);
    }


    @PostMapping("/accept/{diaryId}")
    public ResponseEntity<BaseResponse> acceptDiary(
            @PathVariable("diaryId") String diaryId,
            @RequestBody JoinDiaryRequest request,
            @AuthenticationPrincipal User user
            ){
        Long diarySeq = Long.parseLong(diaryId);
        diaryService.joinAcceptDiary(diarySeq, request, user);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                JOIN_DIARY_SUCCESS_MESSAGE,
                diaryId),
                HttpStatus.OK);
    }

    @PostMapping("/refuse/{diaryId}")
    public ResponseEntity<BaseResponse> refuseDiary(
            @PathVariable("diaryId") String diaryId,
            @AuthenticationPrincipal User user
    ) {
        Long diarySeq = Long.parseLong(diaryId);
        diaryService.joinRefuseDiary(diarySeq, user);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                REFUSE_DIARY_SUCCESS_MESSAGE,
                diaryId),
                HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse> getDiaryList(@AuthenticationPrincipal User user){
        return new ResponseEntity<>(BaseResponse.from(
                true,
                SEARCH_DIARY_SUCCESS_MESSAGE,
                diaryService.getDiaryList(user)),
                HttpStatus.OK);
    }

    @PutMapping("/{diaryId}")
    public ResponseEntity<?> updateDiary(
            @PathVariable Long diaryId,
            @RequestBody UpdateDiaryRequest request,
            @AuthenticationPrincipal User user
            ){
        diaryService.update(diaryId, request, user);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                UPDATE_DIARY_SUCCESS_MESSAGE,
                diaryId),
                HttpStatus.OK);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<BaseResponse> deleteDiary(
            @PathVariable Long diaryId,
            @AuthenticationPrincipal User user
    ){
        diaryService.withdraw(diaryId, user);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                WITHDRAW_DIARY_SUCCESS_MESSAGE,
                diaryId),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<SearchUserResponse> searchUsers(@RequestParam String userName,
                                                @RequestBody SearchUserRequest request,
                                                @AuthenticationPrincipal User user) {
        return diaryService.searchUserByName(userName, request, user);
    }
}

