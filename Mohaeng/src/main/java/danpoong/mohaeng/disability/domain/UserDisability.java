package danpoong.mohaeng.disability.domain;

import danpoong.mohaeng.course.domain.Course;
import danpoong.mohaeng.trip_type.TripType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_disability")
@NoArgsConstructor
@Getter
@Setter
public class UserDisability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number")
    private Long number;

    @ManyToOne
    @JoinColumn(name = "disability_number", nullable = false)
    public Disability disability;

    @ManyToOne
    @JoinColumn(name = "course_number", nullable = false)
    public Course course;

    @Builder
    public UserDisability(Disability disability, Course course) {
        this.disability = disability;
        this.course = course;
    }
}
