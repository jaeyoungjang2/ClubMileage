package com.project.ClubMileage.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Photo {

    @Id
    @GeneratedValue
    private Long id;

    private String photoUuid;
}
