package com.tody.dayori.page.exception;

import com.tody.dayori.common.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import static com.tody.dayori.page.constant.PageConstant.*;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PageUnauthorizedException extends BadRequestException {
    public PageUnauthorizedException() { super(UNAUTHORIZED_USER_ERROR_MESSAGE); }
}
