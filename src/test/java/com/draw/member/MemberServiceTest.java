package com.draw.member;

import com.draw.domain.member.domain.Member;
import com.draw.domain.member.dto.MemberDto;
import com.draw.domain.member.dao.MemberRepository;
import com.draw.domain.member.application.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    MemberService memberService;

    MemberDto.SignUp signUpDto_정상;

    @BeforeEach
    void setUp() {
        signUpDto_정상 = new MemberDto.SignUp(
                "email@naver.com",
                "password",
                "password",
                "nickname");
    }

    @Nested
    @DisplayName("회원가입 테스트")
    class SignUpTest {

        @Test
        @DisplayName("회원가입에 성공합니다")
        void signupTestWhenSuccess() {
            Member 정상회원 = Member.builder()
                    .email(signUpDto_정상.getEmail())
                    .password(signUpDto_정상.getPassword())
                    .nickname(signUpDto_정상.getNickname())
                    .build();

            when(memberRepository.save(any(Member.class))).thenReturn(정상회원);

            Member member = memberService.signup(signUpDto_정상);

            Assertions.assertThat(member).isNotNull();
            Assertions.assertThat(member.getEmail()).isEqualTo(정상회원.getEmail());
            Assertions.assertThat(member.getNickname()).isEqualTo(정상회원.getNickname());
            verify(memberRepository).save(any(Member.class));
        }

        @Test
        @DisplayName("이미 존재하는 이메일로 회원가입시 실패합니다")
        void signupTestWhenEmailAlreadyExists() {
            Member 기존회원 = Member.builder()
                    .email(signUpDto_정상.getEmail())
                    .password(signUpDto_정상.getPassword())
                    .nickname(signUpDto_정상.getNickname())
                    .build();
            when(memberRepository.findByEmail(signUpDto_정상.getEmail())).thenReturn(Optional.of(기존회원));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                memberService.signup(signUpDto_정상);
            });

            Assertions.assertThat(exception.getMessage()).isEqualTo("이미 존재하는 이메일입니다.");
            verify(memberRepository, never()).save(any(Member.class));
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않으면 회원가입에 실패합니다")
        void signupTestWhenPasswordsDoNotMatch() {
            MemberDto.SignUp DTO_비밀번호다름 = new MemberDto.SignUp(
                    "email@naver.com",
                    "password",
                    "password1",
                    "nickname");

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                memberService.signup(DTO_비밀번호다름);
            });

            Assertions.assertThat(exception.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");
            verify(memberRepository, never()).save(any(Member.class));
        }
    }
}