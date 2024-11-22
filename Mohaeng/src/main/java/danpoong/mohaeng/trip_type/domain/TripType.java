package danpoong.mohaeng.trip_type.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "trip_type")
@Getter
public class TripType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number")
    private Long number;

    @Column(name = "name")
    private String name;
}
