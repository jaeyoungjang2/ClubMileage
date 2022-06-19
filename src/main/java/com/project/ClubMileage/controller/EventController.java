package com.project.ClubMileage.controller;

import com.project.ClubMileage.dto.request.EventsRequestDto;
import com.project.ClubMileage.service.EventService;
import com.project.ClubMileage.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EventController {

    private final EventService eventService;

    @PostMapping("/events")
    public void events(@RequestBody EventsRequestDto eventsRequestDto) {
        eventService.happens(eventsRequestDto);
    }
}
