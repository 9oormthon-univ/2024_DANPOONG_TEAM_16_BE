package danpoong.mohaeng.course.service;

import danpoong.mohaeng.area.domain.Area;
import danpoong.mohaeng.area.repository.AreaRepository;
import danpoong.mohaeng.course.domain.Course;
import danpoong.mohaeng.course.domain.UserCourse;
import danpoong.mohaeng.course.dto.*;
import danpoong.mohaeng.course.repository.CourseRepository;
import danpoong.mohaeng.course.repository.UserCourseRepository;
import danpoong.mohaeng.disability.domain.UserDisability;
import danpoong.mohaeng.disability.repository.DisabilityRepository;
import danpoong.mohaeng.disability.repository.UserDisabilityRepository;
import danpoong.mohaeng.location.domain.Location;
import danpoong.mohaeng.location.repository.LocationRepository;
import danpoong.mohaeng.trip_type.domain.UserTripType;
import danpoong.mohaeng.trip_type.repository.TripTypeRepository;
import danpoong.mohaeng.trip_type.repository.UserTripTypeRepository;
import danpoong.mohaeng.user.domain.User;
import danpoong.mohaeng.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;
    private final AreaRepository areaRepository;
    private final LocationRepository locationRepository;
    private final DisabilityRepository disabilityRepository;
    private final TripTypeRepository tripTypeRepository;
    private final UserTripTypeRepository userTripTypeRepository;
    private final UserDisabilityRepository userDisabilityRepository;
    private final CourseRepository courseRepository;
    private final AIRecService aiRecService;



    public CourseCreateRes createTrip(CourseCreateReq courseCreateReq) {
        // 코스 정보 생성
        Course course = createCourse(courseCreateReq);

        // 코스 장애 정보 생성
        createCourseDisability(course, courseCreateReq.getDisability());

        // 코스 여행 타입 정보 생성
        createCourseTripType(course, courseCreateReq.getTripType());

        return crateCourseRes(course, courseCreateReq);
    }

    private Course createCourse(CourseCreateReq courseCreateReq) {
        User user = userRepository.findUserByUuid(courseCreateReq.getUuid());
        Area area = areaRepository.findByNumber(courseCreateReq.getArea());
        LocalDate endDate = courseCreateReq.getStartDate().plusDays(courseCreateReq.getPeriod());

        Course course = Course.builder()
                .name(courseCreateReq.getCourseName())
                .startDate(courseCreateReq.getStartDate())
                .endDate(endDate)
                .period(courseCreateReq.getPeriod())
                .gpsX(courseCreateReq.getGpsX())
                .gpsY(courseCreateReq.getGpsY())
                .user(user)
                .area(area)
                .build();

        courseRepository.save(course);
        log.info("코스 정보 : {}", course);

        return course;
    }

    private void createCourseDisability(Course course, List<Long> Disability) {
        for (Long disabilityNum : Disability) {
            UserDisability userDisability = UserDisability.builder()
                    .course(course)
                    .disability(disabilityRepository.findDisabilitiesByNumber(disabilityNum + 1L))
                    .build();

            userDisabilityRepository.save(userDisability);
            log.info("코스 장애 정보 : {}", userDisability);
        }
    }

    private void createCourseTripType(Course course, List<Long> tripType) {
        for (Long tripTypeNum : tripType) {
            UserTripType userTripType = UserTripType.builder()
                    .course(course)
                    .tripType(tripTypeRepository.findTripTypeByNumber(tripTypeNum + 1))
                    .build();

            userTripTypeRepository.save(userTripType);
            log.info("코스 여행 정보 : {}", userTripType);
        }
    }

    private CourseCreateRes crateCourseRes(Course course, CourseCreateReq courseCreateReq) {
        Long day1 = 1L;
        Long day2 = 2L;
        Long day3 = 3L;

        // 코스 내 관광지 정보 생성
        crateUserCourse(course, courseCreateReq.getDay1(), day1);
        crateUserCourse(course, courseCreateReq.getDay2(), day2);
        crateUserCourse(course, courseCreateReq.getDay3(), day3);

        return CourseCreateRes.builder()
                .uuid(courseCreateReq.getUuid())
                .course(course)
                .disability(courseCreateReq.getDisability())
                .startDate(course.getStartDate())
                .endDate(course.getEndDate())
                .day1(courseCreateReq.getDay1())
                .day2(courseCreateReq.getDay2())
                .day3(courseCreateReq.getDay3())
                .build();
    }

    private void crateUserCourse(Course course, List<Long> locations, Long day) {
        if (locations == null)
            return;

        for (Long location : locations) {
            UserCourse userCourse = UserCourse.builder()
                    .course(course)
                    .day(day)
                    .location(locationRepository.findLocationByContentId(location))
                    .build();

            userCourseRepository.save(userCourse);
            log.info("코스 관광지 정보 : {}", userCourse.getLocation().getContentTitle());
        }
    }

    public Long createAIRecCourse(AICourseReq aiCourseReq) {

        // 필터링 된 관광지
        List<Location> filteredLocation = locationRepository.filterByAreaAndDisabilityAndTravelType(
                        aiCourseReq.getArea(), aiCourseReq.getDisability(), aiCourseReq.getTripType()).stream()
                .limit(80)
                .toList();

        // 필터링 된 음식점
        List<Location> filteredRestaurant = locationRepository.filterByAreaAndContentType(aiCourseReq.getArea()).stream()
                .limit(80)
                .toList();

        Long courseNumber = aiRecService.generateCourse(filteredLocation, filteredRestaurant, aiCourseReq.getArea(), aiCourseReq.getPeriod());

        createCourseDisability(courseRepository.findCourseByNumber(courseNumber), aiCourseReq.getDisability());
        createCourseTripType(courseRepository.findCourseByNumber(courseNumber), aiCourseReq.getTripType());

        return courseNumber;
    }

     public boolean deleteCourseByNum(Long courseNumber) {

        // 순서대로 코스 정보 삭제
        userDisabilityRepository.deleteByCourseId(courseNumber);

        userTripTypeRepository.deleteByCourseId(courseNumber);

        userCourseRepository.deleteByCourseId(courseNumber);

        if (!courseRepository.existsById(courseNumber)) {
            return false;
        }
        courseRepository.deleteById(courseNumber);
        return true;
    }


    public  boolean deletedLocationFromCourse(Long courseNumber, Long location) {
        Optional<UserCourse> userCourse = userCourseRepository.findByCourseNumberAndLocationId(courseNumber, location);

        if (userCourse.isEmpty()) {
            return false;
        }

        userCourseRepository.delete(userCourse.get());
        return true;
    }

    public CourseSearchResponse getCourseDetail(Long courseNumber){
        Course course = courseRepository.findById(courseNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 코스 정보가 없습니다."));

        List<Long> disabilities = course.getDisabilities().stream()
                .map(disability -> disability.getNumber())
                .collect(Collectors.toList());

        List<UserCourse> userCourses = userCourseRepository.findByCourseNumber(courseNumber);


        Map<Long, List<CourseSearchResponse.LocationInfo>> dayWiseLocations = userCourses.stream()
                .collect(Collectors.groupingBy(
                        UserCourse::getDay,
                        Collectors.mapping(
                                uc -> CourseSearchResponse.LocationInfo.builder()
                                        .name(uc.getLocation().getContentTitle())
                                        .address(uc.getLocation().getAddr())
                                        .imageUrl(uc.getLocation().getOriginalImage())
                                        .build(),
                                Collectors.toList()
                        )
                ));


        return CourseSearchResponse.builder()
                .courseNumber(course.getNumber())
                .courseName(course.getName())
                .area(course.getArea().getName())
                .startDate(course.getStartDate())
                .endDate(course.getEndDate())
                .period(course.getPeriod())
                .disability(disabilities)
                .day1(dayWiseLocations.getOrDefault(1L, List.of()))
                .day2(dayWiseLocations.getOrDefault(2L, List.of()))
                .day3(dayWiseLocations.getOrDefault(3L, List.of()))
                .build();
    }

}
