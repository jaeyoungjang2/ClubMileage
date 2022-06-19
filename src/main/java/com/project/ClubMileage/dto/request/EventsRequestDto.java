package com.project.ClubMileage.dto.request;

import com.project.ClubMileage.util.EventAction;
import java.util.List;
import lombok.Getter;

@Getter
public class EventsRequestDto {

    private String type;

    private EventAction action;

    private String reviewId;

    private String content;

    private List<String> attachedPhotoIds;

    private String userId;

    private String placeId;
}
