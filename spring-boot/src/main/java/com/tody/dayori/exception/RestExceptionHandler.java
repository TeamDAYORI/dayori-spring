package com.tody.dayori.exception;

import com.tody.dayori.common.exception.NotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        return ResponseEntity.status(ex.getCode())
                .body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(NotMatchException.class)
    public ResponseEntity<ErrorDto> handleException(NotMatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(ex.getMessage()));
    }
}
