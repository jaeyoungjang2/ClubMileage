package com.project.ClubMileage.domain;

import com.project.ClubMileage.util.Timestamped;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PointHistory extends Timestamped {

    public PointHistory(PointStatus pointStatus) {
        this.pointStatus = pointStatus;
    }

    public PointHistory(PointStatus contentReview, int score) {
        this.pointStatus = pointStatus;
        this.change = score;
    }

    public enum PointStatus {
        FIRST_REVIEW, PHOTO_REVIEW, CONTENT_REVIEW
    }

    private String memberId;

    private int change;

    @Enumerated(EnumType.STRING)
    private PointStatus pointStatus;
}
