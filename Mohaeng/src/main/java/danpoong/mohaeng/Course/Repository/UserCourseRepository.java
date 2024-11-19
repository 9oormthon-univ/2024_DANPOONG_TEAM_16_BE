package danpoong.mohaeng.Course.Repository;


import danpoong.mohaeng.Course.Domain.Course;
import danpoong.mohaeng.Course.Domain.UserCourse;
import danpoong.mohaeng.Location.Domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    UserCourse findUserCourseByCourseAndLocation(Course course, Location location);

    List<UserCourse> findUserCoursesByCourse(Course course);
}
