package com.draw.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //400
    INVALID_INPUT_VALUE("입력 값이 올바르지 않습니다."),

    //401
    DUPLICATE_EMAIL("이미 존재하는 이메일입니다."),

    //404
    NOT_FOUND_MEMBER("회원을 찾을 수 없습니다."),

    //500
    INTERNAL_SERVER_ERROR("서버 내부에 문제가 발생했습니다."),
    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
