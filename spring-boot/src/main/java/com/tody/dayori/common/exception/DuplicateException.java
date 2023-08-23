package com.tody.dayori.common.exception;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateException extends DuplicateKeyException {
//    public DuplicateException(){
//        super();
//    }
    public DuplicateException(String message){
        super(message);
    }
}
