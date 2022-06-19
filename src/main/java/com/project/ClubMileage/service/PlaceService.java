package com.project.ClubMileage.service;

import com.project.ClubMileage.domain.Place;
import com.project.ClubMileage.dto.PlaceRequestDto;
import com.project.ClubMileage.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public String save(PlaceRequestDto placeRequestDto) {
        Place place = new Place(placeRequestDto);
        placeRepository.save(place);

        return place.getUuid();
    }
}
