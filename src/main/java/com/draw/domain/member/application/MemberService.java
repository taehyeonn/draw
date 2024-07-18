package com.draw.domain.member.application;

import com.draw.domain.member.dao.MemberRepository;
import com.draw.domain.member.domain.Member;
import com.draw.domain.member.dto.MemberDto;
import com.draw.global.exception.BadRequestException;
import com.draw.global.exception.ErrorCode;
import com.draw.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member signup(MemberDto.SignUp dto) {
        if (!dto.getPassword().equals(dto.getPassword_confirm())) {
            throw new BadRequestException(ErrorCode.INVALID_INPUT_VALUE);
        }
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UnauthorizedException(ErrorCode.DUPLICATE_EMAIL);
        }
        Member member = Member.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        return memberRepository.save(member);
    }
}