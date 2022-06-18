package com.project.ClubMileage.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    private List<Photo> photoes = new ArrayList<>();

    private Place place;
}
