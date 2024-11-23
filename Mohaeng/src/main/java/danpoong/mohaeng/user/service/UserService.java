package danpoong.mohaeng.user.service;

import danpoong.mohaeng.course.repository.CourseRepository;
import danpoong.mohaeng.course.repository.UserCourseRepository;
import danpoong.mohaeng.user.domain.User;
import danpoong.mohaeng.user.dto.UserCourseRes;
import danpoong.mohaeng.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;
    private final CourseRepository courseRepository;

    public boolean createUser(String uuid) {
        if (checkUserExist(uuid))
            return false;

        User createUser = new User(uuid);
        userRepository.save(createUser);

        return true;
    }

    public boolean checkUserExist(String uuid) {
        return userRepository.existsByUuid(uuid);
    }

    public List<UserCourseRes> getCourseList(String uuid) {
        return courseRepository.findAllByUser(userRepository.findUserByUuid(uuid)).stream()
                .map(course -> {
                    var userCourse = userCourseRepository.findFirstByCourseAndDay(course, 1L);
                    String originalImage = (userCourse != null && userCourse.getLocation() != null)
                            ? userCourse.getLocation().getOriginalImage()
                            : null;

                    // originalImage가 null인 경우 건너뛰기
                    return originalImage != null ? UserCourseRes.builder()
                            .course(course)
                            .originalImage(originalImage)
                            .build() : null;
                })
                .filter(Objects::nonNull) // null을 필터링
                .toList();
    }
}
