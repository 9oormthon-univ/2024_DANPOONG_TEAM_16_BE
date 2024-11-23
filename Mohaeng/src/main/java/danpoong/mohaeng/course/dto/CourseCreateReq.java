package danpoong.mohaeng.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Schema(description = "코스 생성 요청 DTO")
public class CourseCreateReq {

    @Schema(description = "코스 ID", example = "25")
    private Long courseNumber;

    @Schema(description = "사용자 UUID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String uuid;

    @Schema(description = "코스 이름", example = "서울 여행 코스")
    private String courseName;

    @Schema(description = "여행 시작 날짜", example = "2024-12-01")
    private LocalDate startDate;
}
