package danpoong.mohaeng.location.controller;

import danpoong.mohaeng.location.dto.LocationInfoRes;
import danpoong.mohaeng.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationControllerImpl implements LocationController {

    private final LocationService locationService;

    @GetMapping("/{contentId}")
    public ResponseEntity<LocationInfoRes> getInfo(@PathVariable("contentId") Long contentId) {
        LocationInfoRes locationInfoRes = locationService.getLocationInfo(contentId);

        if (locationInfoRes == null)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(locationInfoRes);
    }
}
