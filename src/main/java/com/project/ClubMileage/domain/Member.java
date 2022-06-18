package com.project.ClubMileage.domain;

import com.project.ClubMileage.dto.request.MemberRequestDto;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    @OneToOne
    private Point point;

    public Member(MemberRequestDto memberRequestDto) {
        this.username = memberRequestDto.getId();
        this.password = memberRequestDto.getPassword();
    }
}
