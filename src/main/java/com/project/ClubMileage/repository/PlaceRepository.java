package com.project.ClubMileage.repository;

import com.project.ClubMileage.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Place findByUuid(String placeId);
}
