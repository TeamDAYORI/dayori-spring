package com.tody.dayori.page.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class CreatePageRequest {
    private Long diaryId;
    private String title;
    private String content;
}
