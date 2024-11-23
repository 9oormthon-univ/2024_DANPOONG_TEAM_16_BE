package danpoong.mohaeng.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "AI 추천 코스 요청 객체")
public class AICourseReq
{

    @Schema(description = "사용자의 장애 유형 ID 리스트 (예: 휠체어 접근 가능)", example = "[1, 2, 3]")
    private List<Long> disability;

    @Schema(description = "사용자의 여행 타입 ID 리스트 (예: 자연, 문화 등)", example = "[101, 102]")
    private List<Long> tripType;

    @Schema(description = "여행할 지역 ID", example = "1", required = true)
    private Long area;

    @Schema(description = "여행 기간 (일 단위)", example = "3", required = true)
    private Long period;
}