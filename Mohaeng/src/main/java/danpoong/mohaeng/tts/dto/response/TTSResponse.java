package danpoong.mohaeng.tts.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TTSResponse {
    @JsonProperty("choices")
    private List<Choice> choices;
}
