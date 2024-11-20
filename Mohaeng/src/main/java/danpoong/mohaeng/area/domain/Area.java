package danpoong.mohaeng.area.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "area")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Area {
    @Id
    @Column(name = "number")
    private Long number;

    @Column(name = "name")
    private String name;
}
