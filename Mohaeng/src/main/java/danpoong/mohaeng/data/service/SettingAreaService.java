package danpoong.mohaeng.data.service;

import danpoong.mohaeng.area.domain.Area;
import danpoong.mohaeng.area.repository.AreaRepository;
import danpoong.mohaeng.data.util.JsonParsingUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingAreaService {

    @Value("${openapi.service-key}")
    private String serviceKey;

    private final AreaRepository areaRepository;

    String defaultURL = "https://apis.data.go.kr/B551011/KorWithService1/";
    String defaultParam = "&MobileOS=IOS&MobileApp=BEF&_type=json";

    private static final Logger log = LoggerFactory.getLogger(SettingAreaService.class);

    public void settingAreaData() {

        StringBuilder result = new StringBuilder();

        // BaseURL
        String baseApiUrl = defaultURL + "areaCode1?" + defaultParam;
        // Param
        String service = "&serviceKey=" + serviceKey;
        String rowParam = "numOfRows=20";

        try {
            // API URL 설정
            URL url = new URI(baseApiUrl + service + rowParam).toURL();
            JSONArray items = JsonParsingUtil.parsingJsonFromUrl(url, result);

            if (items == null)
                return ;

            // 'code'와 'name' 추출
            List<Area> areaList = new ArrayList<>();

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String code = item.getString("code");
                String name = item.getString("name");

                log.info("Code: {}", code);
                log.info("Name: {}", name);

                // Area 객체 생성 및 리스트에 추가
                Area area = new Area(Long.parseLong(code), name);
                areaList.add(area);
            }

            // 데이터 DB 삽입 (여기서는 생략)
            areaRepository.saveAll(areaList);
            log.info("Areas inserted successfully into the database.");

        } catch (Exception e) {
            log.error("An error occurred while calling the API or processing data: ", e);
        }
    }
}
