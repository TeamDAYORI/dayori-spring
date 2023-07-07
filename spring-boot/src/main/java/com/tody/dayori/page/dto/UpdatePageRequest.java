package com.tody.dayori.page.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatePageRequest {
    private Long pageId;
    private String title;
    private String content;
}
