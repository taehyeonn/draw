package com.draw.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SignUp {
        @NotBlank(message = "이메일을 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;

        @NotBlank(message = "비밀번호 확인을 입력해주세요.")
        private String password_confirm;

        @NotBlank(message = "닉네임을 입력해주세요.")
        private String nickname;
    }
}