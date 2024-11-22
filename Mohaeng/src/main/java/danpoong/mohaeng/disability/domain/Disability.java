package danpoong.mohaeng.disability.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "disability")
@Getter
public class Disability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number")
    private Long number;

    @Column(name = "name")
    private String name;
}