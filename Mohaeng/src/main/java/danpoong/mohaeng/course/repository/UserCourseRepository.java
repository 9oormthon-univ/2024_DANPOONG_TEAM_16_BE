package danpoong.mohaeng.course.repository;

import danpoong.mohaeng.course.domain.Course;
import danpoong.mohaeng.course.domain.UserCourse;
import danpoong.mohaeng.location.domain.Location;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    UserCourse findUserCourseByCourseAndLocation(Course course, Location location);
    List<UserCourse> findUserCoursesByCourse(Course course);
    @Transactional
    @Modifying
    @Query("DELETE FROM UserCourse uc WHERE uc.course.number = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT uc FROM UserCourse uc WHERE uc.course.number = :courseNumber AND uc.location.contentId = :locationId")
    Optional<UserCourse> findByCourseNumberAndLocationId(@Param("courseNumber") Long courseNumber, @Param("locationId") Long locationId);

    @Query("SELECT uc FROM UserCourse uc WHERE uc.course.number = :courseNumber")
    List<UserCourse> findByCourseNumber(@Param("courseNumber") Long courseNumber);
}
