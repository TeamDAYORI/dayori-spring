package com.tody.dayori.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCommentRequest {
    private Long commentId;
    private String content;
}
