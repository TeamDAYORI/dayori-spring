package com.tody.dayori.page.dto;

import com.tody.dayori.comment.domain.Comment;
import com.tody.dayori.comment.dto.CommentInfoResponse;
import com.tody.dayori.page.domain.Page;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SearchPageResponse {
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime date;
    private List<CommentInfoResponse> comments;
}
