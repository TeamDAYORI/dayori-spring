package com.tody.dayori.page.exception;

import com.tody.dayori.common.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.tody.dayori.page.constant.PageConstant.NOT_MY_TURN_MESSAGE;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PageNotMyTurnException extends BadRequestException {
    public PageNotMyTurnException() {super(NOT_MY_TURN_MESSAGE);}
}
