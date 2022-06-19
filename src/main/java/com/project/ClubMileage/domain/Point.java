package com.project.ClubMileage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class Point {

    @Id
    @GeneratedValue
    private Long id;

    private int myPoint;

    public Point() {
        this.myPoint = 0;
    }

    public void addPoint() {
        this.myPoint ++;
    }
}
