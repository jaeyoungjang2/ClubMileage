package com.project.ClubMileage.service;

import com.project.ClubMileage.domain.Member;
import com.project.ClubMileage.domain.Photo;
import com.project.ClubMileage.domain.Place;
import com.project.ClubMileage.domain.Review;
import com.project.ClubMileage.domain.ReviewPointStatus;
import com.project.ClubMileage.dto.PostRequestDto;
import com.project.ClubMileage.dto.PostReviseRequestDto;
import com.project.ClubMileage.repository.PhotoRepository;
import com.project.ClubMileage.repository.MemberRepository;
import com.project.ClubMileage.repository.PlaceRepository;
import com.project.ClubMileage.repository.ReviewPointStatusRepository;
import com.project.ClubMileage.repository.ReviewRepository;
import com.project.ClubMileage.util.EventAction;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PhotoRepository photoRepository;
    private final PlaceRepository placeRepository;
    private final ReviewPointStatusRepository reviewPointStatusRepository;

    public void save(PostRequestDto postRequestDto, List<String> imageUrls) throws JSONException {
        Member member = memberRepository.findByUuid(postRequestDto.getUserId());
        Place place = placeRepository.findByUuid(postRequestDto.getPlaceId());

        ReviewPointStatus reviewPointStatus = new ReviewPointStatus();
        reviewPointStatusRepository.save(reviewPointStatus);

        Review review = new Review(postRequestDto, member, place, reviewPointStatus);
        reviewRepository.save(review);

        List<Photo> photos = new ArrayList<>();
        for (String imageUrl : imageUrls) {
            photos.add(new Photo(imageUrl, review));
        }

        photoRepository.saveAll(photos);

        sendPostEventRequest(review, postRequestDto.getUserId(), EventAction.ADD);
    }

    @Transactional
    public void revise(PostReviseRequestDto postReviseRequestDto, List<String> imageUrls)
        throws JSONException {
        Member member = memberRepository.findByUuid(postReviseRequestDto.getUserId());
        Place place = placeRepository.findByUuid(postReviseRequestDto.getPlaceId());
        Review review = reviewRepository.findByUuid(postReviseRequestDto.getReviewId());

        List<Photo> photos = new ArrayList<>();
        for (String imageUrl : imageUrls) {
            photos.add(new Photo(imageUrl, review));
        }

        review.changeContent(postReviseRequestDto.getContent());
        review.changePhotos(photos);

        sendPostEventRequest(review, postReviseRequestDto.getUserId(), EventAction.MOD);
    }

    private void sendPostEventRequest(Review review, String userUuid,
        EventAction eventAction) throws JSONException {
        String url = "http://localhost:8080/events";

        RestTemplate restTemplate = new RestTemplate(); // 비동기 전달
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();

        List<String> attachedPhotoIds = new ArrayList();
        List<Photo> photos = review.getPhotos();
        for (Photo photo : photos) {
            attachedPhotoIds.add(photo.getUuid());
        }
        jsonObject.put("type", "REVIEW");
        jsonObject.put("attachedPhotoIds", attachedPhotoIds);
        jsonObject.put("action", eventAction.getValue());
        jsonObject.put("reviewId", review.getUuid());
        jsonObject.put("content", review.getContent());
        jsonObject.put("userId", userUuid);
        jsonObject.put("placeId", review.getPlace().getUuid());

        HttpEntity<String> logRequest = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        restTemplate.postForEntity(url, logRequest, String.class);
    }
}
