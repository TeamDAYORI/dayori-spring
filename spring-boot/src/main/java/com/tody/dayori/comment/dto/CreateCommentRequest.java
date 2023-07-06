package com.tody.dayori.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class CreateCommentRequest {
    private Long pageId;
    private String content;
}
