package com.tody.dayori.diary.dto;

import com.tody.dayori.diary.domain.Diary;
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

    public Diary toEntity() {
        return Diary.builder()
                .diaryTitle(title)
                .diaryCover(cover)
                .diaryDuration(duration)
                .diaryPassword(password)
                .build();
    }
}
