package danpoong.mohaeng.course.service;

import danpoong.mohaeng.area.repository.AreaRepository;
import danpoong.mohaeng.course.domain.Course;
import danpoong.mohaeng.course.domain.UserCourse;
import danpoong.mohaeng.course.dto.ChatGPTRes;
import danpoong.mohaeng.course.repository.CourseRepository;
import danpoong.mohaeng.course.repository.UserCourseRepository;
import danpoong.mohaeng.location.domain.Location;
import danpoong.mohaeng.location.repository.LocationRepository;
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
                "temperature", 0,
                "content", generatePrompt(locations, restaurants, areaRepository.findByNumber(area).getName(), period)
        )));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + openAiApiKey);

        // OpenAI API 호출
        ResponseEntity<ChatGPTRes> response = restTemplate.postForEntity(
                openAiApiUrl, new HttpEntity<>(body, headers), ChatGPTRes.class);

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
                if (userCourse.getLocation() == null)
                    continue;
                userCourseRepository.save(userCourse);
            }
        }

        return course.getNumber();
    }

    private String generatePrompt(List<Location> locations, List<Location> restaurants, String area, Long period) {

        StringBuilder prompt = new StringBuilder();
        Long tripPeriod = period + 1L;
        String defaultPrompt = "I want to receive a detailed travel course recommendation for " + tripPeriod + " days in " + area + ".";

        prompt.append(defaultPrompt);
        prompt.append(" Please use the Tourist and Restaurant lists I provide, which include gpsX and gpsY coordinates, to calculate travel distances and estimated travel times." +
                " For each day, include exactly 3 places with contentTypeId 12 (e.g., tourist attractions) and 1 place with contentTypeId 39 (e.g., restaurants)." +
                " Ensure that the travel sequence minimizes travel time (preferably within 15–30 km between places) while providing a logical and enjoyable flow for the day." +
                " Return the course in the following structure, formatted to match the regex pattern '(Day \\d+): \\[(.*?)\\]':" +
                " For example, 'Day 1: [contentId1, contentId2, contentId3, contentId4]' and 'Day 2: [contentId5, contentId6, contentId7, contentId8]'." +
                " Each line should represent one day's recommended locations in the specified order. Ensure the courses are evenly distributed across the days, with a balance and variety of the selected locations.");
        // 프롬프트 캐싱
        // ptu 서비스 - 개인 단계에서 조금 어려움

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
