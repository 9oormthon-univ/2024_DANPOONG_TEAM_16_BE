package danpoong.mohaeng.Area.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Area {
    @Id
    @Column(name = "area_code")
    private Long areaCode;
    @Column(name = "area_name")
    private String areaName;

    public Area(Long areaCode, String areaName) {
        this.areaCode = areaCode;
        this.areaName = areaName;
    }
}
