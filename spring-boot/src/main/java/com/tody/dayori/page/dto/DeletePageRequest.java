package com.tody.dayori.page.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeletePageRequest {
    private Long pageId;

    @Builder
    public DeletePageRequest(Long pageId) {
        this.pageId = pageId;
    }
}
