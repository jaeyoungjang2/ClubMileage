package com.project.ClubMileage.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    // user uuid
    private String userId;

    private String content;

    private String placeId;
}
