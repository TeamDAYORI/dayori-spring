package com.tody.dayori.page.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SearchPageResponse {
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime date;
}
