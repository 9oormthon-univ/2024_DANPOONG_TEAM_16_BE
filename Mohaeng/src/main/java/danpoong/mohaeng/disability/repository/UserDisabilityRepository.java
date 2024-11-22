package danpoong.mohaeng.disability.repository;

import danpoong.mohaeng.disability.domain.UserDisability;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDisabilityRepository extends JpaRepository<UserDisability, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM UserDisability ud WHERE ud.course.number = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);
}
