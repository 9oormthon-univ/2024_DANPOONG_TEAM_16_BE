package danpoong.mohaeng.trip_type.repository;

import danpoong.mohaeng.trip_type.domain.UserTripType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserTripTypeRepository extends JpaRepository<UserTripType, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM UserDisability ud WHERE ud.course.number = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);
}
