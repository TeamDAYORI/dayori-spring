package com.tody.dayori.page.dto;

import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@Builder
public class CreatePageRequest {
    private String title;
    private String content;
    private LocalDateTime date = LocalDateTime.now();
    private User userInfo;
    private Diary diary;
    public Page toEntity() {
        return Page.builder()
                .title(title)
                .content(content)
                .date(date)
                .userInfo(userInfo)
                .diary(diary)
                .build();
    }
}
