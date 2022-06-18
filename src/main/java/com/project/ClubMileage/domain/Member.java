package com.project.ClubMileage.domain;

import com.project.ClubMileage.dto.request.MemberRequestDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String uuid = UUID.randomUUID().toString();

    private String username;

    private String password;

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    @OneToOne
    private Point point;

    public Member(MemberRequestDto memberRequestDto) {
        this.username = memberRequestDto.getId();
        this.password = memberRequestDto.getPassword();
    }
}
