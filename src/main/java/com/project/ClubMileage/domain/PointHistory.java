package com.project.ClubMileage.domain;

import com.project.ClubMileage.util.Timestamped;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PointHistory extends Timestamped {

    public enum PointStatus {
        FIRST_REVIEW, PHOTO_REVIEW, CONTENT_REVIEW
    }

    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;

    private int changePoint;

    private int currentPoint;

    @Enumerated(EnumType.STRING)
    private PointStatus pointStatus;

    public PointHistory(PointStatus pointStatus, int score, Long memberId, int currentPoint) {
        this.pointStatus = pointStatus;
        this.changePoint = score;
        this.currentPoint = currentPoint;
        this.memberId = memberId;
    }
}
