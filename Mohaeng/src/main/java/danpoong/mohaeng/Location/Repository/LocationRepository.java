package danpoong.mohaeng.location.repository;

import danpoong.mohaeng.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findLocationByContentId(Long contentId);
}