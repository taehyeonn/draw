package com.draw.domain.member.api;

import com.draw.domain.member.application.MemberService;
import com.draw.domain.member.domain.Member;
import com.draw.domain.member.dto.MemberDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid MemberDto.SignUp signupDto) {
        Member member = memberService.signup(signupDto);
        return ResponseEntity.created(URI.create(member.getEmail())).build();
    }
}