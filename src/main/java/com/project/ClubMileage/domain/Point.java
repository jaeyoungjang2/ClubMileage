package com.project.ClubMileage.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Point {

    @Id
    @GeneratedValue
    private Long id;

    private String reviewUuid;

    private String content;

    private List<String> attachedPhotoUuids = new ArrayList<>();

    private String userUuid;

    private String placeUuid;
}
