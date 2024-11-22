package danpoong.mohaeng.course.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatGPTRes {
    @JsonProperty("choices")
    private List<Choice> choice;
}
