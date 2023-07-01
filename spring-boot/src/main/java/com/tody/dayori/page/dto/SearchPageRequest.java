package com.tody.dayori.page.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SearchPageRequest {

    private Long pageId;
    @Builder
    public SearchPageRequest(Long pageId) {
        this.pageId = pageId;
    }
}
