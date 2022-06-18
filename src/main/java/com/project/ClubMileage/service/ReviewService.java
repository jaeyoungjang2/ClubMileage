package com.project.ClubMileage.service;

import com.project.ClubMileage.domain.Member;
import com.project.ClubMileage.domain.Photo;
import com.project.ClubMileage.domain.Place;
import com.project.ClubMileage.domain.Review;
import com.project.ClubMileage.dto.PostRequestDto;
import com.project.ClubMileage.dto.request.EventsRequestDto;
import com.project.ClubMileage.repository.PhotoRepository;
import com.project.ClubMileage.repository.MemberRepository;
import com.project.ClubMileage.repository.PlaceRepository;
import com.project.ClubMileage.repository.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
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

    public void happens(EventsRequestDto eventsRequestDto) {
        // add 일 경우 이벤트 저장
        // mod 일 경우 이벤트 수정
        // delete 일 경우 이벤트 삭제
    }

    public void save(PostRequestDto postRequestDto, List<String> imageUrls) throws JSONException {
        Member member = memberRepository.findByUuid(postRequestDto.getUserId());
        Place place = placeRepository.findByUuid(postRequestDto.getPlaceId());

        List<Photo> photos = new ArrayList<>();
        for (String imageUrl : imageUrls) {
            photos.add(new Photo(imageUrl));
        }
        photoRepository.saveAll(photos);

        Review review = new Review(postRequestDto, member, photos, place);
        reviewRepository.save(review);

        sendPostEventRequest(review);
    }

    private void sendPostEventRequest(Review review) throws JSONException {
        String url = "http://localhost:8080/events";

        RestTemplate restTemplate = new RestTemplate(); // 비동기 전달
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", "REVIEW");
        jsonObject.put("action", "ADD");
        jsonObject.put("reviewId", review.getUuid());
        jsonObject.put("content", review.getContent());
//        jsonObject.put("attachedPhotoids", );
//        jsonObject.put("userId", );
//        jsonObject.put("placeId",);

        HttpEntity<String> logRequest = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        restTemplate.postForEntity(url, logRequest, String.class);
    }
}
