package com.project.ClubMileage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Photo {

    @Id
    @GeneratedValue
    private Long id;

    private String photoUuid;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
}
