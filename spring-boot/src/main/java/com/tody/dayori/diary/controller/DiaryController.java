package com.tody.dayori.diary.controller;

import com.tody.dayori.common.dto.BaseResponse;
import com.tody.dayori.diary.domain.UserDiary;
import com.tody.dayori.diary.dto.CreateDiaryRequest;
import com.tody.dayori.diary.dto.JoinDiaryRequest;
import com.tody.dayori.diary.service.DiaryServiceImpl;
import com.tody.dayori.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.tody.dayori.page.constant.PageConstant.CREATE_PAGE_SUCCESS_MESSAGE;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryServiceImpl diaryService;

    @PostMapping("")
    public ResponseEntity<BaseResponse> createDiary(@RequestBody CreateDiaryRequest request){
        Long diaryId = diaryService.create(request);
        Map<String, Long> response = new HashMap<>();
        response.put("diaryId", diaryId);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                CREATE_PAGE_SUCCESS_MESSAGE,
                diaryId),
                HttpStatus.OK);
    }

    @GetMapping("/{diaryId}/invcode")
    public ResponseEntity<?> getInvCode(
            @PathVariable("diaryId") Long diaryId
            ){
        return ResponseEntity.ok(diaryService.getInvCode(diaryId));
    }

    @PostMapping("/join/{invCode}")
    public ResponseEntity<BaseResponse> joinDiary(
            @PathVariable("invCode") String invCode,
            @RequestBody JoinDiaryRequest request
            ){
        byte[] base64Bytes = invCode.getBytes();
        byte[] idBytes = Base64.decodeBase64(base64Bytes);
        String idString = new String(idBytes);
        Long diaryId = Long.parseLong(idString);
        diaryService.joinDiary(diaryId, request);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                CREATE_PAGE_SUCCESS_MESSAGE,
                diaryId),
                HttpStatus.OK);
    }



}
