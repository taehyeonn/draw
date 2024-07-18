package com.draw.global.exception;

public class BadRequestException extends CustomException{

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}