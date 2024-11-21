package danpoong.mohaeng.area.repository;

import danpoong.mohaeng.area.domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {
    Area findByNumber(Long number);
}
