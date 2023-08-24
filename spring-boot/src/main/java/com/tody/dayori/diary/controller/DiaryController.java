package com.tody.dayori.diary.controller;

import com.tody.dayori.auth.entity.User;
import com.tody.dayori.common.dto.BaseResponse;
import com.tody.dayori.diary.dto.CreateDiaryRequest;
import com.tody.dayori.diary.dto.JoinDiaryRequest;
import com.tody.dayori.diary.dto.SearchUserResponse;
import com.tody.dayori.diary.dto.UpdateDiaryRequest;
import com.tody.dayori.diary.service.DiaryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BaseResponse> createDiary(@RequestBody CreateDiaryRequest request){
        Long diaryId = diaryService.create(request);
        Map<String, Long> response = new HashMap<>();
        response.put("diaryId", diaryId);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                CREATE_DIARY_SUCCESS_MESSAGE,
                diaryId),
                HttpStatus.OK);
    }

//    @GetMapping("/{diaryId}/invcode")
//    public ResponseEntity<?> getInvCode(
//            @PathVariable("diaryId") Long diaryId
//            ){
//        return ResponseEntity.ok(diaryService.getInvCode(diaryId));
//    }

    @PostMapping("/accept/{diaryId}")
    public ResponseEntity<BaseResponse> acceptDiary(
            @PathVariable("diaryId") String diaryId,
            @RequestBody JoinDiaryRequest request
            ){
//        byte[] base64Bytes = invCode.getBytes();
//        byte[] idBytes = Base64.decodeBase64(base64Bytes);
//        String idString = new String(idBytes);
        Long diarySeq = Long.parseLong(diaryId);
        diaryService.joinAcceptDiary(diarySeq, request);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                JOIN_DIARY_SUCCESS_MESSAGE,
                diaryId),
                HttpStatus.OK);
    }

    @PostMapping("/refuse/{diaryId}")
    public ResponseEntity<BaseResponse> refuseDiary(
            @PathVariable("diaryId") String diaryId
    ) {
        Long diarySeq = Long.parseLong(diaryId);
        diaryService.joinRefuseDiary(diarySeq);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                REFUSE_DIARY_SUCCESS_MESSAGE,
                diaryId),
                HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse> getDiaryList(){
        return new ResponseEntity<>(BaseResponse.from(
                true,
                SEARCH_DIARY_SUCCESS_MESSAGE,
                diaryService.getDiaryList()),
                HttpStatus.OK);
    }

    @PutMapping("/{diaryId}")
    public ResponseEntity<?> updateDiary(
            @PathVariable Long diaryId,
            @RequestBody UpdateDiaryRequest request
            ){
        diaryService.update(diaryId, request);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                UPDATE_DIARY_SUCCESS_MESSAGE,
                diaryId),
                HttpStatus.OK);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<BaseResponse> deleteDiary(
            @PathVariable Long diaryId
    ){
        diaryService.withdraw(diaryId);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                WITHDRAW_DIARY_SUCCESS_MESSAGE,
                diaryId),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<SearchUserResponse> searchUsers(@RequestParam String userName) {
        return diaryService.searchUserByName(userName);
    }
}

