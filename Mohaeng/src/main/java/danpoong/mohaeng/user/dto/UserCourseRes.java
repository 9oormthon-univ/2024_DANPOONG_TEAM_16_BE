package danpoong.mohaeng.user.dto;

import danpoong.mohaeng.course.domain.Course;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserCourseRes {
    private Long courseNumber;
    private String courseName;
    private String area;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public UserCourseRes(Course course) {
        this.courseNumber = course.getNumber();
        this.courseName = course.getName();
        this.area = course.getArea().getName();
        this.startDate = course.getStartDate();
        this.endDate = course.getEndDate();
    }
}
