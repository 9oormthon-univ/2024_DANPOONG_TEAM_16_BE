package danpoong.mohaeng.disabled.repository;

import danpoong.mohaeng.disabled.domain.Disabled;
import danpoong.mohaeng.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisabledRepository extends JpaRepository<Disabled, Long> {
    Disabled findDisabledByLocation(Location location);
    Boolean existsByLocation(Location location);
}


