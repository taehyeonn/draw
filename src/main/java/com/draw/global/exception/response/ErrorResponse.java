package com.draw.global.exception.response;

import com.draw.global.exception.CustomException;
import com.draw.global.exception.ErrorCode;

public record ErrorResponse(ErrorCode errorCode, String message) {

    public static ErrorResponse from(CustomException customException) {
        return ErrorResponse.from(customException.getErrorCode());
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode, errorCode.getMessage());
    }
}