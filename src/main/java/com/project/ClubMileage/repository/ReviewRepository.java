package com.project.ClubMileage.repository;

import com.project.ClubMileage.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
