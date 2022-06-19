package com.project.ClubMileage.service;

import com.project.ClubMileage.domain.Member;
import com.project.ClubMileage.domain.Point;
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
        Point point = new Point();
        Member member = new Member(memberRequestDto, point);

        memberRepository.save(member);
    }
}
