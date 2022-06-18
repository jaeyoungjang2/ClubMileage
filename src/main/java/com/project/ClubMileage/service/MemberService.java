package com.project.ClubMileage.service;

import com.project.ClubMileage.domain.Member;
import com.project.ClubMileage.dto.request.MemberRequestDto;
import com.project.ClubMileage.repository.MemberRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void join(MemberRequestDto memberRequestDto) {
        Member member = new Member(memberRequestDto);
        memberRepository.save(member);
    }
}
