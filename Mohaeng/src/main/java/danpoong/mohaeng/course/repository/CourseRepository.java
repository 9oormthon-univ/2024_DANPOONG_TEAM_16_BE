package danpoong.mohaeng.course.repository;

import danpoong.mohaeng.course.domain.Course;
import danpoong.mohaeng.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByUser(User user);
}
