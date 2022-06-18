package com.project.ClubMileage.repository;

import com.project.ClubMileage.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Photo, Long> {

}
