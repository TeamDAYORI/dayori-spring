package com.tody.dayori.page.exception;

import com.tody.dayori.common.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.tody.dayori.page.constant.PageConstant.NOT_EXIST_PAGE_MESSAGE;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PageNotFoundException extends BadRequestException {
    public PageNotFoundException() {
        super(NOT_EXIST_PAGE_MESSAGE);
    }
}
