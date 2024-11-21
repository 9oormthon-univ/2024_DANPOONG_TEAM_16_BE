package danpoong.mohaeng.location.service;

import danpoong.mohaeng.disabled.repository.DisabledRepository;
import danpoong.mohaeng.location.domain.Location;
import danpoong.mohaeng.location.dto.LocationInfoRes;
import danpoong.mohaeng.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final DisabledRepository disabledRepository;

    public LocationInfoRes getLocationInfo(Long contentId) {
        Location location = locationRepository.findLocationByContentId(contentId);

        return LocationInfoRes.infoBuilder(location, disabledRepository.findDisabledByLocation(location));
    }
}
