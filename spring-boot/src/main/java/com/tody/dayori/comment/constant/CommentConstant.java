package com.tody.dayori.comment.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentConstant {
    public static final String CREATE_COMMENT_SUCCESS_MESSAGE = "댓글 등록에 성공했습니다.";
    public static final String CREATE_COMMENT_FAIL_MESSAGE = "댓글 등록에 실패했습니다.";
    public static final String SEARCH_COMMENT_SUCCESS_MESSAGE = "댓글 조회에 성공했습니다.";
    public static final String NOT_EXIST_COMMENT_MESSAGE = "댓글가 존재하지 않습니다.";
    public static final String DELETE_COMMENT_SUCCESS_MESSAGE = "댓글를 삭제했습니다.";
}
