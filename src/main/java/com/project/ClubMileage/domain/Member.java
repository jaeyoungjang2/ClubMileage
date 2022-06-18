package com.project.ClubMileage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Point point;
}
