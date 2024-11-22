package danpoong.mohaeng.trip_type.domain;

import danpoong.mohaeng.course.domain.Course;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_trip_type")
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

    @Builder
    public UserTripType(TripType tripType, Course course) {
        this.tripType = tripType;
        this.course = course;
    }
}
