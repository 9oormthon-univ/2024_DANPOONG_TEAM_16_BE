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

    @ManyToOne
    @JoinColumn(name = "trip_type_number", nullable = false)
    public TripType tripType;

    @ManyToOne
    @JoinColumn(name = "course_number", nullable = false)
    public Course course;
}
