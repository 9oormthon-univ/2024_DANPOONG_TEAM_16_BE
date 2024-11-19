package danpoong.mohaeng.Location.Repository;


import danpoong.mohaeng.Location.Domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findLocationByContentId(Long contentId);
}