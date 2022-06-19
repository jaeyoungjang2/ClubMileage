package com.project.ClubMileage.domain;

import com.project.ClubMileage.dto.PlaceRequestDto;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue
    private Long id;

    private String uuid = UUID.randomUUID().toString();

    private String name;

    public Place(PlaceRequestDto placeRequestDto) {
        this.name = placeRequestDto.getName();
    }
}
