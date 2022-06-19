package com.project.ClubMileage.repository;

import com.project.ClubMileage.domain.Place;
import com.project.ClubMileage.domain.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPlace(Place place);
}
