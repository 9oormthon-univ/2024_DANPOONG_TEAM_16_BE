package danpoong.mohaeng.course.repository;

import danpoong.mohaeng.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
