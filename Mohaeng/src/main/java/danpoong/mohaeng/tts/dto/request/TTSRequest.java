package danpoong.mohaeng.tts.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import danpoong.mohaeng.tts.dto.*;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TTSRequest {
    @JsonProperty("model")
    private String model;
    @JsonProperty("messages")
    private List<Message> messages;
    @JsonProperty("max_tokens")
    private int maxTokens;

    public static TTSRequest createImageRequest(String model, int maxTokens, String role, String requestText, String imageUrl) {
        TextContent textContent = new TextContent("text", requestText);
        ImageContent imageContent = new ImageContent("image_url", new ImageUrl(imageUrl));
        Message message = new ImageMessage(role, List.of(textContent, imageContent));
        return createTTSRequest(model, maxTokens, Collections.singletonList(message));
    }

    public static TTSRequest createTextRequest(String model, int maxTokens, String role, String requestText) {
        Message message = new TextMessage(role, requestText);
        return createTTSRequest(model, maxTokens, Collections.singletonList(message));
    }

    private static TTSRequest createTTSRequest(String model, int maxTokens, List<Message> messages) {
        return TTSRequest.builder()
                .model(model)
                .maxTokens(maxTokens)
                .messages(messages)
                .build();
    }
}