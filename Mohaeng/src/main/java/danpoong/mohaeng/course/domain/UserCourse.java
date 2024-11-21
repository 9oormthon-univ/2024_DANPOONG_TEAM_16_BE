package danpoong.mohaeng.course.domain;


import danpoong.mohaeng.location.domain.Location;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_course")
@NoArgsConstructor
@Getter
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_course_number")
    private Long userCourseNumber;

    @ManyToOne
    @JoinColumn(name = "course_number")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Location location;

    @Builder
    public UserCourse(Course course, Location location) {
        this.course = course;
        this.location = location;
    }
}
