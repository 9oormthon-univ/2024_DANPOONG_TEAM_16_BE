package danpoong.mohaeng.disability.repository;

import danpoong.mohaeng.disability.domain.Disability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisabilityRepository extends JpaRepository<Disability, Long> {
    Disability findDisabilitiesByNumber(Long number);
}
