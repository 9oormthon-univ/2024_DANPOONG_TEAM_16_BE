package danpoong.mohaeng.course.service;

import danpoong.mohaeng.area.domain.Area;
import danpoong.mohaeng.area.repository.AreaRepository;
import danpoong.mohaeng.course.domain.Course;
import danpoong.mohaeng.course.domain.UserCourse;
import danpoong.mohaeng.course.dto.AICourseRes;
import danpoong.mohaeng.course.dto.CourseCreateReq;
import danpoong.mohaeng.course.dto.CourseCreateRes;
import danpoong.mohaeng.course.repository.CourseRepository;
import danpoong.mohaeng.course.repository.UserCourseRepository;
import danpoong.mohaeng.disability.domain.UserDisability;
import danpoong.mohaeng.disability.repository.DisabilityRepository;
import danpoong.mohaeng.disability.repository.UserDisabilityRepository;
import danpoong.mohaeng.location.domain.Location;
import danpoong.mohaeng.location.repository.LocationRepository;
import danpoong.mohaeng.user.domain.User;
import danpoong.mohaeng.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;
    private final AreaRepository areaRepository;
    private final LocationRepository locationRepository;
    private final DisabilityRepository disabilityRepository;
    private final UserDisabilityRepository userDisabilityRepository;
    private final CourseRepository courseRepository;
    private final AIRecService aiRecService;

    public CourseCreateRes createTrip(CourseCreateReq courseCreateReq) {
        // 코스 정보 생성
        Course course = createCourse(courseCreateReq);

        // 코스 장애 정보 생성
        createCourseDisability(course, courseCreateReq.getDisability());

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
                    .disability(disabilityRepository.findDisabilitiesByNumber(disabilityNum))
                    .build();

            userDisabilityRepository.save(userDisability);
            log.info("코스 장애 정보 : {}", userDisability);
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
            return ;

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

    public AICourseRes createAIRecCourse(List<Long> disability, List<Long> tripType, Long area, Long period) {

        // 필터링 된 관광지
        List<Location> filteredLocation = locationRepository.filterByAreaAndDisabilityAndTravelType(
                        area, disability, tripType).stream()
                .limit(80)
                .toList();

        // 필터링 된 음식점
        List<Location> filteredRestaurant = locationRepository.filterByAreaAndContentType(area).stream()
                .limit(80)
                .toList();

        return aiRecService.generateCourse(filteredLocation, filteredRestaurant, areaRepository.findByNumber(area).getName(), period);
    }
}
