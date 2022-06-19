package com.project.ClubMileage.domain;

import com.project.ClubMileage.dto.PlaceRequestDto;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Place {

    @Id
    @GeneratedValue
    private Long id;

    private String uuid;

    private String name;

    public Place(PlaceRequestDto placeRequestDto) {
        this.name = placeRequestDto.getName();
    }
}
