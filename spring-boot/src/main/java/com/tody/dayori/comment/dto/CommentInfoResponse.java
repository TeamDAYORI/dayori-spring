package com.tody.dayori.comment.dto;

import com.tody.dayori.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentInfoResponse {
    private String username;
    private String userImg;
    private LocalDateTime date;
    private String content;
}
