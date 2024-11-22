package danpoong.mohaeng.tts.dto.request;

import danpoong.mohaeng.tts.dto.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ImageMessage extends Message {
    private List<Content> content;

    public ImageMessage(String role, List<Content> content) {
        super(role);
        this.content = content;
    }
}
