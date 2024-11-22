package danpoong.mohaeng.course.domain;

import danpoong.mohaeng.area.domain.Area;
import danpoong.mohaeng.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "course")
@NoArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number")
    private Long number;

    @Column(name = "name")
    private String name;

    @Column(name = "period")
    private Long period;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "gps_x")
    private Double gpsX;

    @Column(name = "gps_y")
    private Double gpsY;

    @ManyToOne
    @JoinColumn(name = "uuid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "area_number")
    private Area area;

    @Builder
    public Course(String name, Long period, LocalDate startDate, LocalDate endDate, Double gpsX, Double gpsY, User user, Area area) {
        this.name = name;
        this.period = period;
        this.startDate = startDate;
        this.endDate = endDate;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.user = user;
        this.area = area;
    }
}
