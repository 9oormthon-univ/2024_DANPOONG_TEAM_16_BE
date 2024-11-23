package danpoong.mohaeng.course.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AICourseReq {

    private List<Long> disability;
    private List<Long> tripType;
    private Long area;
    private Long period;
}
