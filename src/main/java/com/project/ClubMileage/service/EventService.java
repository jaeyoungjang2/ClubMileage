package com.project.ClubMileage.service;

import static com.project.ClubMileage.domain.PointHistory.PointStatus.CONTENT_REVIEW;
import static com.project.ClubMileage.domain.PointHistory.PointStatus.FIRST_REVIEW;
import static com.project.ClubMileage.domain.PointHistory.PointStatus.PHOTO_REVIEW;

import com.project.ClubMileage.domain.Member;
import com.project.ClubMileage.domain.Place;
import com.project.ClubMileage.domain.PointHistory;
import com.project.ClubMileage.domain.Review;
import com.project.ClubMileage.domain.ReviewPointStatus;
import com.project.ClubMileage.dto.request.EventsRequestDto;
import com.project.ClubMileage.repository.MemberRepository;
import com.project.ClubMileage.repository.PlaceRepository;
import com.project.ClubMileage.repository.PointHistoryRepository;
import com.project.ClubMileage.repository.PointRepository;
import com.project.ClubMileage.repository.ReviewRepository;
import com.project.ClubMileage.util.EventAction;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventService {

    private final PointHistoryRepository pointHistoryRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

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

        if (eventsRequestDto.getAction().equals(EventAction.MOD)) {
            if (review.getReviewPointStatus().isContentReview()
                && eventsRequestDto.getContent().length() < 1) {
                reviewPointStatus.changeContentReviewStatus();
            }

            if (review.getReviewPointStatus().isPhotoReview() && eventsRequestDto.getAttachedPhotoIds().size() < 1) {
                member.getPoint().addPoint();
                reviewPointStatus.changePhotoReviewStatus();
            }
        }

        // delete 일 경우 이벤트 삭제
    }
}
