package com.project.ClubMileage.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String uuid = UUID.randomUUID().toString();;

    private String content;

    @OneToMany(mappedBy = "review")
    private List<Photo> photoes = new ArrayList<>();

    @OneToOne
    private Place place;
}
