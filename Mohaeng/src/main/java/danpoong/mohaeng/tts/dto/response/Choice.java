package danpoong.mohaeng.tts.dto.response;

import danpoong.mohaeng.tts.dto.TextMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
    private int index;
    private TextMessage message;
}