package com.tody.dayori.diary.dto;

import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.domain.UserDiary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateDiaryRequest {

    private String title;
    private String cover;
    private Integer duration;
    private String password;

}

