package danpoong.mohaeng.Area.Repository;


import danpoong.mohaeng.Area.Domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {
    Area findByAreaCode(Long areaCode);
}
