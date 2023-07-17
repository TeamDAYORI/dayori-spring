package com.tody.dayori.diary.controller;

import com.tody.dayori.diary.dto.CreateDiaryRequest;
import com.tody.dayori.diary.service.DiaryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryServiceImpl diaryService;

    @PostMapping("/diary")
    public ResponseEntity<?> createDiary(@RequestBody CreateDiaryRequest request){
        Long diaryId = diaryService.create(request);
        Map<String, Long> response = new HashMap<>();
        response.put("diaryId", diaryId);
        return ResponseEntity.ok(response);
    }
}
