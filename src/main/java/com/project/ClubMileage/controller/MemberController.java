package com.project.ClubMileage.controller;

import com.project.ClubMileage.dto.request.MemberRequestDto;
import com.project.ClubMileage.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/users")
    public String join(@RequestBody MemberRequestDto memberRequestDto) {
        return memberService.join(memberRequestDto);
    }
}
