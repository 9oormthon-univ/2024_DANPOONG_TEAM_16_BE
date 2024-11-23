package danpoong.mohaeng.location.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import danpoong.mohaeng.disabled.domain.QDisabled;
import danpoong.mohaeng.location.domain.Location;
import danpoong.mohaeng.location.domain.QLocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LocationRepositoryImpl implements LocationRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Set<Location> filterByAreaAndDisabilityAndTravelType(Long area, List<Long> disabilities, List<Long> tripTypes) {
        BooleanBuilder conditions = new BooleanBuilder();

        QLocation l = QLocation.location;
        QDisabled d = QDisabled.disabled;

        Long locationId = 12L;

        // 관광지만 선택 및 지역 조건 추가
        conditions.and(addContentTypeAndAreaConditions(locationId, area));

        // 장애 정보에 따른 조건 추가
        conditions.and(addDisabilityConditions(disabilities));

        // 여행 타입 조건 추가
        conditions.and(addTripTypeConditions(tripTypes));

        var query = queryFactory.selectFrom(l)
                .join(l.disabled, d)
                .where(conditions);

        List<Location> resultList = query.fetch();

        log.info("list size : {}", resultList.size());
        return new HashSet<>(resultList);  // 중복 제거를 위해 Set으로 변환
    }

    @Override
    public Set<Location> filterByAreaAndContentType(Long area) {
        BooleanBuilder conditions = new BooleanBuilder();

        QLocation l = QLocation.location;
        Long restaurantId = 39L;

        // 관광지만 선택 및 지역 조건 추가
        conditions.and(addContentTypeAndAreaConditions(restaurantId, area));

        var query = queryFactory.selectFrom(l)
                .where(conditions);

        List<Location> resultList = query.fetch();

        log.info("list size : {}", resultList.size());
        return new HashSet<>(resultList);  // 중복 제거를 위해 Set으로 변환
    }

    private BooleanBuilder addContentTypeAndAreaConditions(Long contentType, Long area) {
        BooleanBuilder contentTypeAndAreaConditions = new BooleanBuilder();

        QLocation l = QLocation.location;
        contentTypeAndAreaConditions.and(l.contentTypeId.eq(contentType));
        contentTypeAndAreaConditions.and(l.area.number.eq(area));

        return contentTypeAndAreaConditions;
    }

    private BooleanBuilder addDisabilityConditions(List<Long> disabilities) {
        BooleanBuilder disabilityConditions = new BooleanBuilder();

        QDisabled d = QDisabled.disabled;

        for (Long disability : disabilities) {
            if (disability == 0 || disability == 1) {
                disabilityConditions.and(d.elevator.isNotNull())
                        .and(d.entrance.isNotNull())
                        .and(d.publicTransport.isNotNull());
            }
            else if (disability == 2) {
                disabilityConditions.and(d.braileBlock.isNotNull())
                        .and(d.guideHuman.isNotNull());
            }
            else if (disability == 3) {
                disabilityConditions.and(d.signGuide.isNotNull()
                        .or(d.videoGuide.isNotNull())
                        .or(d.hearingRoom.isNotNull())
                        .or(d.hearingHandicapEtc.isNotNull()));
            }
            else if (disability == 4) {
                disabilityConditions.and(d.stroller.isNotNull()
                        .or(d.lactationRoom.isNotNull())
                        .or(d.babySpareChair.isNotNull())
                        .or(d.infantsFamilyEtc.isNotNull()));
            }
        }

        return disabilityConditions;
    }

    private BooleanBuilder addTripTypeConditions(List<Long> tripTypes) {
        BooleanBuilder travelTypeConditions = new BooleanBuilder();

        QLocation l = QLocation.location;

        List<String> forestType = Arrays.asList("숲", "휴양림", "산림욕장", "치유");
        List<String> oceanType = Arrays.asList("해수욕장", "바다", "물놀이", "호수");
        List<String> cultureType = Arrays.asList("박물관", "미술관", "역사", "문화", "사찰");
        List<String> outsideType = Arrays.asList("가족", "어린이", "공원", "파크", "레저");

        for (Long tripType : tripTypes) {
            if (tripType == 0) {
                for (String type : forestType) {
                    travelTypeConditions.or(l.description.contains(type));
                }
            }
            else if (tripType == 1){
                for (String type : oceanType) {
                    travelTypeConditions.or(l.description.contains(type));
                }
            }
            else if (tripType == 2){
                for (String type : cultureType) {
                    travelTypeConditions.or(l.description.contains(type));
                }
            }
            else if (tripType == 3){
                for (String type : outsideType) {
                    travelTypeConditions.or(l.description.contains(type));
                }
            }
        }

        return travelTypeConditions;
    }
}
