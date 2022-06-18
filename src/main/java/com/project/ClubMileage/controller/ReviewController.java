package com.project.ClubMileage.controller;

import com.project.ClubMileage.dto.PostRequestDto;
import com.project.ClubMileage.dto.request.EventsRequestDto;
import com.project.ClubMileage.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/posts")
    public void save(@RequestBody PostRequestDto postRequestDto) throws JSONException {
        reviewService.save();
    }

    @PostMapping("/events")
    public void events(@RequestBody EventsRequestDto eventsRequestDto) {
        reviewService.happens(eventsRequestDto);
    }
}
