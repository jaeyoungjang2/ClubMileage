package com.project.ClubMileage.service;

import static com.project.ClubMileage.domain.PointHistory.PointStatus.*;

import com.project.ClubMileage.domain.Member;
import com.project.ClubMileage.domain.Photo;
import com.project.ClubMileage.domain.Place;
import com.project.ClubMileage.domain.PointHistory;
import com.project.ClubMileage.domain.Review;
import com.project.ClubMileage.domain.ReviewPointStatus;
import com.project.ClubMileage.dto.PostRequestDto;
import com.project.ClubMileage.dto.PostReviseRequestDto;
import com.project.ClubMileage.dto.request.EventsRequestDto;
import com.project.ClubMileage.repository.PhotoRepository;
import com.project.ClubMileage.repository.MemberRepository;
import com.project.ClubMileage.repository.PlaceRepository;
import com.project.ClubMileage.repository.PointHistoryRepository;
import com.project.ClubMileage.repository.PointRepository;
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
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final ReviewPointStatusRepository reviewPointStatusRepository;

    @Transactional
    public void happens(EventsRequestDto eventsRequestDto) {
        System.out.println("event happens");
        Place place = placeRepository.findByUuid(eventsRequestDto.getPlaceId());
        Member member = memberRepository.findByUuid(eventsRequestDto.getUserId());
        Review review = reviewRepository.findByUuid(eventsRequestDto.getReviewId());
        System.out.println("member.getId() = " + member.getId());

        ReviewPointStatus reviewPointStatus = review.getReviewPointStatus();

        // 텍스트가 1글자 이상인 경우 1점
        // 1장 이상의 사진이 있을 경우 1점
        // 특정 장소의 첫 리뷰일 경우 -> 사진이 존재하는 리뷰이고, 해당 장소에 리뷰가 없을 경우

        // 리뷰 저장
        if (eventsRequestDto.getAction().equals(EventAction.ADD)) {
            if (eventsRequestDto.getContent().length() >= 1) {
                member.getPoint().addPoint();
                pointHistoryRepository.save(new PointHistory(CONTENT_REVIEW, 1, member.getId(), member.getPoint().getScore()));
                reviewPointStatus.changeContentReviewStatus();
            }
            if (eventsRequestDto.getAttachedPhotoIds().size() >= 1) {
                member.getPoint().addPoint();
                pointHistoryRepository.save(new PointHistory(PHOTO_REVIEW, 1, member.getId(), member.getPoint().getScore()));
                reviewPointStatus.changePhotoReviewStatus();
            }
            // 보너스 점수 (사진이 존재하는 리뷰이고, 해당 장소에 리뷰가 없을 경우)
            // 리뷰를 저장한 뒤 상황이기 때문에 해당 장소에 리뷰가 1개 있을 경우로 계산
            if (reviewRepository.findByPlace(place).size() == 1) {
                member.getPoint().addPoint();
                pointHistoryRepository.save(new PointHistory(FIRST_REVIEW, 1, member.getId(), member.getPoint().getScore()));
                reviewPointStatus.changeFirstReviewStatus();
            }
        }


        // add 일 경우 이벤트 저장
        // mod 일 경우 이벤트 수정
        // delete 일 경우 이벤트 삭제
    }

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

    private void sendPostEventRequest(Review review, String userUuid,
        EventAction eventAction) throws JSONException {
        String url = "http://localhost:8080/events";

        RestTemplate restTemplate = new RestTemplate(); // 비동기 전달
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        ArrayList arrayList = new ArrayList();
        arrayList.add("4");
        jsonObject.put("type", "REVIEW");
        jsonObject.put("attachedPhotoIds", arrayList);
        jsonObject.put("action", eventAction.getValue());
        jsonObject.put("reviewId", review.getUuid());
        jsonObject.put("content", review.getContent());
        jsonObject.put("userId", userUuid);
        jsonObject.put("placeId", review.getPlace().getUuid());

        HttpEntity<String> logRequest = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        restTemplate.postForEntity(url, logRequest, String.class);
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
}
