package danpoong.mohaeng.trip_type;

import danpoong.mohaeng.course.domain.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_trip_type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTripType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @Enumerated(EnumType.STRING)
    @Column(name = "trip_type", nullable = false)
    private TripType tripType;

    @ManyToOne
    @JoinColumn(name = "course_number", nullable = false)
    public Course course;
}
