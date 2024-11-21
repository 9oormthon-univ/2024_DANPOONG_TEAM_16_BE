package danpoong.mohaeng.disability.domain;

import danpoong.mohaeng.course.domain.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_disability")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDisability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number")
    private Long number;

    @Enumerated(EnumType.STRING)
    @Column(name = "disability", nullable = false)
    private Disability disability;

    @ManyToOne
    @JoinColumn(name = "course_number", nullable = false)
    public Course course;
}
