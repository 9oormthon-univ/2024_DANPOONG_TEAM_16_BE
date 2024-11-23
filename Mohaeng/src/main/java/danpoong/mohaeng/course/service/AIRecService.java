package danpoong.mohaeng.course.service;

import danpoong.mohaeng.area.repository.AreaRepository;
import danpoong.mohaeng.course.domain.Course;
import danpoong.mohaeng.course.domain.UserCourse;
import danpoong.mohaeng.course.dto.ChatGPTRes;
import danpoong.mohaeng.course.repository.CourseRepository;
import danpoong.mohaeng.course.repository.UserCourseRepository;
import danpoong.mohaeng.location.domain.Location;
import danpoong.mohaeng.location.repository.LocationRepository;
import danpoong.mohaeng.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AIRecService {
    @Value("${openai.api.url}")
    private String openAiApiUrl;

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @Value("${openai.finetuning.model}")
    private String finetuningModel;

    private final RestTemplate restTemplate;
    private final AreaRepository areaRepository;
    private final CourseRepository courseRepository;
    private final LocationRepository locationRepository;
    private final UserCourseRepository userCourseRepository;

    public Long generateCourse(List<Location> locations, List<Location> restaurants, Long area, Long period) {
        Map<String, Object> body = new HashMap<>();

        body.put("model", finetuningModel);
        body.put("messages", List.of(Map.of(
                "role", "user",
                "content", generatePrompt(locations, restaurants, areaRepository.findByNumber(area).getName(), period)
        )));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + openAiApiKey);

        // OpenAI API 호출
        ResponseEntity<ChatGPTRes> response = restTemplate.postForEntity(
                openAiApiUrl, new HttpEntity<>(body, headers), ChatGPTRes.class);

        log.info("Full OpenAI API Response: {}", response.getBody());

        // 응답 처리
        String aiResponse = response.getBody().getChoice().getFirst().getMessage().getContent();
        log.info("AI Response Content: {}", aiResponse);

        Map<String, List<Long>> dayWiseContentIds = parseDayWiseContentIds(aiResponse);

        return saveAICourse(period, area, dayWiseContentIds);
    }

    private Long saveAICourse(Long period, Long area, Map<String, List<Long>> dayWiseContentIds) {
        Location location = locationRepository.findLocationByContentId(dayWiseContentIds.getOrDefault("Day 1", List.of()).getFirst());

        Course course = Course.builder()
                .period(period)
                .gpsX(location.getGpsX())
                .gpsY(location.getGpsY())
                .area(areaRepository.findByNumber(area))
                .build();

        courseRepository.save(course);

        for (Long day = 1L; day <= 3; day++) {
            List<Long> locaionList = dayWiseContentIds.getOrDefault("Day " + day, List.of());

            for (Long content : locaionList) {
                UserCourse userCourse = new UserCourse(day, course, locationRepository.findLocationByContentId(content));
                userCourseRepository.save(userCourse);
            }
        }

        return course.getNumber();
    }

    private String generatePrompt(List<Location> locations, List<Location> restaurants, String area, Long period) {

        StringBuilder prompt = new StringBuilder();
        Long tripPeriod = period + 1L;

        String defaultPrompt = "나는 " + tripPeriod + "일 동안 " + area + "를 여행할 코스를 추천 받고 싶어. ";
        prompt.append(defaultPrompt);
        prompt.append("내가 보내는 Tourist, Restaurant 리스트에서 장소의 gpsX, gpsY를 기반으로 이동 거리와 시간을 고려해서 코스를 추천해줘. ");
        prompt.append("하루에 contentTypeId가 12인 장소 3곳, contentTypeId가 39인 장소 1곳을 추천해줘. ");
        prompt.append("코스를 추천할 때 Day 1:[contentId 리스트] 와 같은 구조로 제공해줘.");

        // Tourist 리스트 추가
        prompt.append("Tourist : [");
        prompt.append(locations.stream()
                .map(Location::toPrompt)
                .collect(Collectors.joining(", ")));
        prompt.append("]\n");

        // Restaurant 리스트 추가
        prompt.append("Restaurants: [");
        prompt.append(restaurants.stream()
                .map(Location::toPrompt)
                .collect(Collectors.joining(", ")));
        prompt.append("]");

        log.info("Generated Prompt: {}", prompt);
        return prompt.toString();
    }

    private Map<String, List<Long>> parseDayWiseContentIds(String aiResponse) {
        Map<String, List<Long>> dayWiseContentIds = new LinkedHashMap<>();

        // 정규표현식으로 Day X와 해당 리스트 추출
        Pattern pattern = Pattern.compile("(Day \\d+): \\[(.*?)\\]");
        Matcher matcher = pattern.matcher(aiResponse);

        while (matcher.find()) {
            String day = matcher.group(1); // Day 이름 (예: Day 1)
            String[] ids = matcher.group(2).split(","); // contentId 리스트 추출
            List<Long> contentIds = Arrays.stream(ids)
                    .map(String::trim) // 공백 제거
                    .map(Long::parseLong) // Long 타입 변환
                    .collect(Collectors.toList()); // Collectors.toList() 사용
            dayWiseContentIds.put(day, contentIds);
        }

        return dayWiseContentIds;
    }
}
