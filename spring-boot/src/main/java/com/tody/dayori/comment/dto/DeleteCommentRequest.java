package com.tody.dayori.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteCommentRequest {
    private Long commentId;

    @Builder
    public DeleteCommentRequest(Long commentId) {
        this.commentId = commentId;
    }
}
