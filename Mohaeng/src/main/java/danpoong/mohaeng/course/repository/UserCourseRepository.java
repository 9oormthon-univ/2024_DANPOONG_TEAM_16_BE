package danpoong.mohaeng.course.repository;

import danpoong.mohaeng.course.domain.Course;
import danpoong.mohaeng.course.domain.UserCourse;
import danpoong.mohaeng.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    UserCourse findUserCourseByCourseAndLocation(Course course, Location location);
    List<UserCourse> findUserCoursesByCourse(Course course);
}
