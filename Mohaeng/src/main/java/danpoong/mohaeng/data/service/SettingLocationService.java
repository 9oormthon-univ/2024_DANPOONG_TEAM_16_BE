package danpoong.mohaeng.data.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import danpoong.mohaeng.area.repository.AreaRepository;
import danpoong.mohaeng.data.util.JsonParsingUtil;
import danpoong.mohaeng.disabled.domain.Disabled;
import danpoong.mohaeng.disabled.repository.DisabledRepository;
import danpoong.mohaeng.location.domain.Location;
import danpoong.mohaeng.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SettingLocationService {
    private final LocationRepository locationRepository;
    private final DisabledRepository disabledRepository;
    private final AreaRepository areaRepository;

    @Value("${openapi.service-key}")
    private String apiKey;
    final String defaultURL = "https://apis.data.go.kr/B551011/KorWithService1/";
    final String defaultParam = "&MobileOS=IOS&MobileApp=BEF&_type=json";

    public void setLocationAndDisabled(String areaCode, String page, String contentTypeId) {

        List<Location> locationList = new ArrayList<>();
        setLocations(locationList, areaCode, page, contentTypeId);

        List<Disabled> disabledList = new ArrayList<>();
        setDisabled(locationList, disabledList);
    }

    public void setLocations(List<Location> locationList, String areaCode, String page, String contentTypeId){

        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder result = new StringBuilder();

        // Base URLs
        String baseAPIUrl = defaultURL + "areaBasedList1?" + defaultParam;

        // Query Parameters
        String contentTypeParam = "&contentTypeId=" + contentTypeId;
        String arrangeParam = "&arrange=R";
        String areaParam = "&areaCode=" + areaCode;
        String pageParam = "&pageNo=" + page;
        String rowParam = "&numOfRows=100";

        // Service key
        String serviceKey = "&serviceKey=" + apiKey; // 서비스키

        try {
            // 지역 기반 관광 정보 조회 api
            URL localUrl = new URI(baseAPIUrl + serviceKey + contentTypeParam + arrangeParam + areaParam + pageParam + rowParam).toURL();
            log.info(localUrl.toString());

            JSONArray items = JsonParsingUtil.parsingJsonFromUrl(localUrl, result);

            if (items == null)
                return ;

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                Location location = objectMapper.readValue(item.toString(), Location.class);

                // Location 엔티티 생성
                location.setArea(areaRepository.findByNumber(item.getLong("areacode")));
                setDescription(location);

                // 기존에 존재하는 location인 경우
                if (locationRepository.existsByContentId(location.getContentId()))
                    continue;

                // 리스트에 추가
                locationList.add(location);
            }

            // 장소 리스트 DB에 저장
            locationRepository.saveAll(locationList);
            log.info("Locations inserted successfully into the database.");
        }
        catch (URISyntaxException e) {
            log.error("An error occurred while making url: ", e);
        }
        catch (IOException e) {
            log.error("An error occurred while parsing and saving location data: ", e);
        }
    }

    public void setDescription(Location location) {
        StringBuilder infoResult = new StringBuilder();

        // Base URL
        String baseAPIUrl = defaultURL + "detailCommon1?" + defaultParam;

        // Query Parameters
        String descriptionParam = "&defaultYN=Y&overviewYN=Y";
        String contentIdParam = "&contentId=" + location.getContentId().toString();

        // Service key
        String serviceKey = "&serviceKey=" + apiKey; // 서비스키

        // 공통 정보 조회 api
        try {
            URL infoUrl = new URI(baseAPIUrl + serviceKey + descriptionParam + contentIdParam).toURL();

            JSONArray infoArray = JsonParsingUtil.parsingJsonFromUrl(infoUrl, infoResult);
            if (infoArray == null)
                return ;

            String description = infoArray.getJSONObject(0).optString("overview", "설명 없음");
            if (description.length() > 255) {
                description = description.substring(0, 255);
            }
            location.setDescription(description);
        }
        catch (IOException e) {
            log.error("An error occurred while parsing and saving Desciption data: ", e);
        } catch (URISyntaxException e) {
            log.error("An error occurred while making url: ", e);
        }
    }

    public void setDisabled(List<Location> locationList, List<Disabled> disabledList) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Base URL
        String baseAPIUrl = defaultURL + "detailWithTour1?" + defaultParam;

        // Service key
        String serviceKey = "&serviceKey=" + apiKey; // 서비스키

        try {
            for (Location location : locationList) {
                StringBuilder disabledResult = new StringBuilder();

                // Query Parameters
                String contentIdParam = "&contentId=" + location.getContentId().toString();

                URL disabledURL = new URI(baseAPIUrl + serviceKey + contentIdParam).toURL();

                JSONArray detailArray = JsonParsingUtil.parsingJsonFromUrl(disabledURL, disabledResult);
                if (detailArray == null)
                    continue;

                Disabled disabled = objectMapper.readValue(detailArray.getJSONObject(0).toString(), Disabled.class);
                addDisabledToList(disabled, location, disabledList);
            }

            if (!disabledList.isEmpty()) {
                disabledRepository.saveAll(disabledList);
                log.info("Locations disabled info inserted successfully into the database.");
            } else {
                log.warn("No disabled data to save.");
            }

        } catch (IOException e) {
            log.error("An error occurred while parsing and saving Disabled data: ", e);
        } catch (URISyntaxException e) {
            log.error("An error occurred while making url: ", e);
        }
    }

    private void addDisabledToList(Disabled disabled, Location location, List<Disabled> disabledList) {
        disabled.setLocation(location);
        log.info("Disabled : " + disabled.getLocation());

        if (!disabledRepository.existsByLocation(disabled.getLocation())) {
            disabledList.add(disabled);
            log.info("Disabled added");
        } else {
            log.info("Duplicate disabled data found for location: " + location.getContentId());
        }
    }
}
