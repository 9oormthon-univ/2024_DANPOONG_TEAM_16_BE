package danpoong.mohaeng.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Schema(description = "코스 조회 응답 DTO")
public class CourseSearchResponse {

    @Schema(description = "코스 번호", example = "1")
    private Long courseNumber;

    @Schema(description = "코스 이름", example = "서울 여행 코스")
    private String courseName;

    @Schema(description = "지역 이름", example = "서울")
    private String area;

    @Schema(description = "여행 시작 날짜", example = "2024-12-01")
    private LocalDate startDate;

    @Schema(description = "여행 종료 날짜", example = "2024-12-03")
    private LocalDate endDate;

    @Schema(description = "여행 기간 (일)", example = "3")
    private Long period;

    @Schema(description = "장애 유형 ID 리스트", example = "[1, 2]")
    private List<Long> disability;

    @Schema(description = "첫째 날 방문 장소 정보", example = "[{name: '경복궁', address: '서울 종로구', imageUrl: 'http://example.com/image.jpg'}]")
    private List<LocationInfo> day1;

    @Schema(description = "둘째 날 방문 장소 정보", example = "[{name: '남산타워', address: '서울 용산구', imageUrl: 'http://example.com/image2.jpg'}]")
    private List<LocationInfo> day2;

    @Schema(description = "셋째 날 방문 장소 정보", example = "[{name: '한강공원', address: '서울 영등포구', imageUrl: 'http://example.com/image3.jpg'}]")
    private List<LocationInfo> day3;

    @Getter
    @Builder
    @Schema(description = "장소 정보")
    public static class LocationInfo {

        @Schema(description = "장소 GPS X 좌표", example = "37.5665")
        private double gpsX;

        @Schema(description = "장소 GPS Y 좌표", example = "126.9780")
        private double gpsY;

        @Schema(description = "콘텐츠 ID", example = "101")
        private Long contentId;

        @Schema(description = "콘텐츠 타입 ID", example = "12")
        private Long contentTypeId;

        @Schema(description = "장소 이름", example = "경복궁")
        private String name;

        @Schema(description = "주소", example = "서울 종로구 사직로 161")
        private String address;

        @Schema(description = "이미지 URL", example = "http://example.com/image.jpg")
        private String imageUrl;
    }

    @Builder
    public CourseSearchResponse(Long courseNumber, String courseName, String area, LocalDate startDate, LocalDate endDate,
                                Long period, List<Long> disability, List<LocationInfo> day1, List<LocationInfo> day2,
                                List<LocationInfo> day3) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.area = area;
        this.startDate = startDate;
        this.endDate = endDate;
        this.period = period;
        this.disability = disability;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
    }
}
