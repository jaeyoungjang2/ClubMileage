package com.project.ClubMileage.controller;

import com.project.ClubMileage.dto.PlaceRequestDto;
import com.project.ClubMileage.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/places")
    public void save(@RequestBody PlaceRequestDto placeRequestDto) {
        placeService.save(placeRequestDto)
    }
}
