package com.project.ClubMileage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Point {

    @Id
    @GeneratedValue
    private Long id;

    private int score;

    public Point() {
        this.score = 0;
    }

    public void addPoint() {
        this.score ++;
    }
}
