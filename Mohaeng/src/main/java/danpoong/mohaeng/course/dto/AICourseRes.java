package danpoong.mohaeng.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "AI 추천 코스 응답 Body")
public class AICourseRes {
    @Schema(description = "여행할 지역 이름",
            example = "Seoul")
    private String area;

    @Schema(description = "여행 시작 위치의 GPS X 좌표",
            example = "37.5665")
    private Double gpsX;

    @Schema(description = "여행 시작 위치의 GPS Y 좌표",
            example = "126.9780")
    private Double gpsY;

    @Schema(description = "여행 기간 (일 단위)",
            example = "3")
    private Long period;

    @Schema(description = "첫째 날 방문 장소 ID 리스트",
            example = "[101, 102, 103]")
    private List<Long> day1;

    @Schema(description = "둘째 날 방문 장소 ID 리스트",
            example = "[201, 202, 203]")
    private List<Long> day2;

    @Schema(description = "셋째 날 방문 장소 ID 리스트",
            example = "[301, 302, 303]")
    private List<Long> day3;

    @Builder
    public AICourseRes(String area, Double gpsX, Double gpsY, Long period) {
        this.area = area;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.period = period;
    }
}
