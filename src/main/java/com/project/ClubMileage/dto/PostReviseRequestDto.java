package com.project.ClubMileage.dto;

import lombok.Getter;

@Getter
public class PostReviseRequestDto {
    // user uuid
    private String userId;

    private String content;

    private String placeId;

    private String reviewId;
}
