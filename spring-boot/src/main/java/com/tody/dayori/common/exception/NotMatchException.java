package com.tody.dayori.common.exception;

public class NotMatchException extends BadRequestException{
    public static final String PASSWORD_NOT_MATCH = "비밀번호가 틀렸습니다.";

    public NotMatchException(String message) {
        super(message);
    }
}
