package danpoong.mohaeng.trip_type.repository;

import danpoong.mohaeng.trip_type.domain.UserTripType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTripTypeRepository extends JpaRepository<UserTripType, Long> {
}
