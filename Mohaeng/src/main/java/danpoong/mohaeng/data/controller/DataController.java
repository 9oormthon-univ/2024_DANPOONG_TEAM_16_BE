package danpoong.mohaeng.data.controller;

import danpoong.mohaeng.data.service.SettingAreaService;
import danpoong.mohaeng.data.service.SettingLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/data")
@Slf4j
@RequiredArgsConstructor
public class DataController {

    private final SettingAreaService settingAreaService;
    private final SettingLocationService settingLocationService;

    @PostMapping("/area")
    public String saveAreas() {
        try {
            settingAreaService.settingAreaData();
            return "Area data inserted successfully!";
        } catch (Exception e) {
            log.error(e.toString());
            return "Error occurred: " + e.getMessage();
        }
    }

    @PostMapping("/location/{areaCode}/{page}")
    public ResponseEntity<String> getLocation(@PathVariable("areaCode") String areaCode,
                                              @PathVariable("page") String page) {
        String CONTENT_ATTRACTION = "12";
        settingLocationService.setLocationAndDisabled(areaCode, page, CONTENT_ATTRACTION);
        return ResponseEntity.ok("Location data inserted successfully!\n");
    }

    @PostMapping("/restaurant/{areaCode}/{page}")
    public ResponseEntity<String> getRestaurant(@PathVariable("areaCode") String areaCode,
                                                @PathVariable("page") String page) {
        String CONTENT_RESTAURANT = "39";
        settingLocationService.setLocationAndDisabled(areaCode, page, CONTENT_RESTAURANT);
        return ResponseEntity.ok("Restaurant data inserted successfully!\n");
    }

}
