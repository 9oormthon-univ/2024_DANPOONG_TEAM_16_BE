package danpoong.mohaeng.trip_type.repository;

import danpoong.mohaeng.trip_type.domain.TripType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripTypeRepository extends JpaRepository<TripType, Long> {
    TripType findTripTypeByNumber(Long number);
}
