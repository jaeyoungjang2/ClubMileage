package com.project.ClubMileage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class ReviewPointStatus {
    @Id
    @GeneratedValue
    private Long id;
    
    private boolean firstReview;
    
    private boolean photoReview;
    
    private boolean contentReview;

    public void changeContentReviewStatus() {
        contentReview = contentReview == true ? false : true;
    }

    public void changePhotoReviewStatus() {
        photoReview = photoReview == true ? false : true;
    }

    public void changeFirstReviewStatus() {
        firstReview = firstReview == true ? false : true;
    }
}
