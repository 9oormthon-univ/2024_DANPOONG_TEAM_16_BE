package danpoong.mohaeng.course.dto;

import danpoong.mohaeng.course.domain.Course;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Schema(description = "코스 생성 응답 DTO")
public class CourseCreateRes {

    @Schema(description = "사용자 UUID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String uuid;

    @Schema(description = "코스 이름", example = "서울 여행 코스")
    private String courseName;

    @Schema(description = "GPS X 좌표", example = "37.5665")
    private Double gpsX;

    @Schema(description = "GPS Y 좌표", example = "126.9780")
    private Double gpsY;

    @Schema(description = "여행 기간 (일)", example = "3")
    private Long period;

    @Schema(description = "장애 유형 ID 리스트", example = "[1, 2]")
    private List<Long> disability;

    @Schema(description = "여행 시작 날짜", example = "2024-12-01")
    private LocalDate startDate;

    @Schema(description = "여행 종료 날짜", example = "2024-12-03")
    private LocalDate endDate;

    @Schema(description = "첫째 날 방문 장소 ID 리스트", example = "[101, 102, 103]")
    private List<Long> day1;

    @Schema(description = "둘째 날 방문 장소 ID 리스트", example = "[201, 202, 203]")
    private List<Long> day2;

    @Schema(description = "셋째 날 방문 장소 ID 리스트", example = "[301, 302, 303]")
    private List<Long> day3;

    @Builder
    public CourseCreateRes(String uuid, Course course, List<Long> disability, LocalDate startDate, LocalDate endDate,
                           List<Long> day1, List<Long> day2, List<Long> day3) {
        this.uuid = uuid;
        this.courseName = course.getName();
        this.gpsX = course.getGpsX();
        this.gpsY = course.getGpsY();
        this.period = course.getPeriod();
        this.disability = disability;
        this.startDate = startDate;
        this.endDate = endDate;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
    }
}
