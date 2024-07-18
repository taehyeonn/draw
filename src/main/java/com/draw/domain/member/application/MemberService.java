package com.draw.domain.member.application;

import com.draw.domain.member.dao.MemberRepository;
import com.draw.domain.member.domain.Member;
import com.draw.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member signup(MemberDto.SignUp dto) {
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        if (!dto.getPassword().equals(dto.getPassword_confirm())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        Member member = Member.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        return memberRepository.save(member);
    }
}