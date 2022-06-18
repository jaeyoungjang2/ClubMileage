package com.project.ClubMileage.domain;

import java.util.UUID;
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

    private String uuid = UUID.randomUUID().toString();

    private String url;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public Photo(String imageUrl) {
        this.url = imageUrl;
    }
}
