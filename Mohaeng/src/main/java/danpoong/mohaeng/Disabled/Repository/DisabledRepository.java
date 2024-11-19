package danpoong.mohaeng.Disabled.Repository;



import danpoong.mohaeng.Disabled.Domain.Disabled;
import danpoong.mohaeng.Location.Domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisabledRepository extends JpaRepository<Disabled, Long> {
    Disabled findDisabledByLocation(Location location);
}


