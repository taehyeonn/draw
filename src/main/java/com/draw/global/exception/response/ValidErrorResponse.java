package com.draw.global.exception.response;

import com.draw.global.exception.ErrorCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

public record ValidErrorResponse(ErrorCode errorCode, String message, Map<String, String> result) {

    public static ValidErrorResponse from(MethodArgumentNotValidException e) {
        Map<String, String> result = e.getBindingResult().getFieldErrors().stream()
                .collect(toMap(FieldError::getField, ValidErrorResponse::getFieldErrorMessage));
        return new ValidErrorResponse(
                ErrorCode.INVALID_INPUT_VALUE,
                ErrorCode.INVALID_INPUT_VALUE.getMessage(),
                result
        );
    }

    private static String getFieldErrorMessage(FieldError error) {
        String message = error.getDefaultMessage();
        if (message == null) {
            return "[ValidErrorResponse] 잘못된 요청입니다.";
        }
        return message;
    }
}