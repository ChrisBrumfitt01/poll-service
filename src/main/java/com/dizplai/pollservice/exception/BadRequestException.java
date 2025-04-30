package com.dizplai.pollservice.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg){
        super(msg);
    }
}
