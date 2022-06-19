package com.project.ClubMileage.domain;

import com.project.ClubMileage.dto.PostRequestDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String uuid = UUID.randomUUID().toString();

    private String content;

    @OneToMany(mappedBy = "review")
    private List<Photo> photos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    private Place place;

    public Review(PostRequestDto postRequestDto, Member member, List<Photo> photos, Place place) {
        this.content = postRequestDto.getContent();
        this.member = member;
        this.photos = photos;
        this.place = place;
    }
}
