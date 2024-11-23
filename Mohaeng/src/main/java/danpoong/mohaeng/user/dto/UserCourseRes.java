package danpoong.mohaeng.user.dto;

import danpoong.mohaeng.course.domain.Course;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Schema(description = "사용자의 코스 정보를 담은 응답 DTO")
public class UserCourseRes {

    @Schema(description = "코스의 고유 번호", example = "123")
    private Long courseNumber;

    @Schema(description = "코스 이름", example = "서울 어드벤처")
    private String courseName;

    @Schema(description = "코스가 위치한 지역 이름", example = "서울")
    private String area;

    @Schema(description = "코스 시작 날짜", example = "2024-01-01")
    private LocalDate startDate;

    @Schema(description = "코스 종료 날짜", example = "2024-01-07")
    private LocalDate endDate;

    @Schema(description = "여행 기간", example = "1")
    private Long period;

    @Schema(description = "원본 이미지", example = "http://tong.visitkorea.or.kr/cms/resource/21/2657021_image2_1.jpg")
    private String originalImage;

    @Builder
    public UserCourseRes(Course course, String originalImage) {
        this.courseNumber = course.getNumber();
        this.courseName = course.getName();
        this.area = course.getArea().getName();
        this.startDate = course.getStartDate();
        this.endDate = course.getEndDate();
        this.period = course.getPeriod();
        this.originalImage = originalImage;
    }
}