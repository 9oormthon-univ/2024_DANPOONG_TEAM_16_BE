package danpoong.mohaeng.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Schema(description = "코스 생성 요청 DTO")
public class CourseCreateReq {

    @Schema(description = "사용자 UUID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String uuid;

    @Schema(description = "코스 이름", example = "서울 여행 코스")
    private String courseName;

    @Schema(description = "지역 번호", example = "1")
    private Long area;

    @Schema(description = "GPS X 좌표", example = "37.5665")
    private Double gpsX;

    @Schema(description = "GPS Y 좌표", example = "126.9780")
    private Double gpsY;

    @Schema(description = "여행 기간 (일)", example = "3")
    private Long period;

    @Schema(description = "장애 유형 ID 리스트", example = "[1, 2]")
    private List<Long> disability;

    @Schema(description = "여행 타입 ID 리스트", example = "[3, 4]")
    private List<Long> tripType;

    @Schema(description = "여행 시작 날짜", example = "2024-12-01")
    private LocalDate startDate;

    @Schema(description = "첫째 날 방문 장소 ID 리스트", example = "[101, 102, 103]")
    private List<Long> day1;

    @Schema(description = "둘째 날 방문 장소 ID 리스트", example = "[201, 202, 203]")
    private List<Long> day2;

    @Schema(description = "셋째 날 방문 장소 ID 리스트", example = "[301, 302, 303]")
    private List<Long> day3;
}
