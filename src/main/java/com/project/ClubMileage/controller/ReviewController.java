package com.project.ClubMileage.controller;

import com.project.ClubMileage.dto.PostRequestDto;
import com.project.ClubMileage.dto.request.EventsRequestDto;
import com.project.ClubMileage.service.ReviewService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public void save(@RequestPart(required = false) PostRequestDto postRequestDto,
        @RequestPart(required = false) List<MultipartFile> images) throws JSONException {

        List<String> imageUrls = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            imageUrls = upload(images);
        }
        reviewService.save(postRequestDto, imageUrls);
    }

    // S3로 파일 업로드하기
    private List<String> upload(List<MultipartFile> files) {
        String dirName = "images";
        List<String> imagesUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = dirName + "/" + UUID.randomUUID() + file.getName();   // S3에 저장된 파일 이름
            imagesUrls.add(fileName);
        }
        return imagesUrls;
    }

    @PostMapping("/events")
    public void events(@RequestBody EventsRequestDto eventsRequestDto) {
        reviewService.happens(eventsRequestDto);
    }
}
