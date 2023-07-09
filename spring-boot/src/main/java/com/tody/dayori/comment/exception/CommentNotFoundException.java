package com.tody.dayori.comment.exception;

import com.tody.dayori.common.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.tody.dayori.comment.constant.CommentConstant.*;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CommentNotFoundException extends BadRequestException {
    public CommentNotFoundException() {
        super(NOT_EXIST_COMMENT_MESSAGE);
    }
}
