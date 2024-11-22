package danpoong.mohaeng.tts.service;

import danpoong.mohaeng.tts.dto.request.TTSRequest;
import danpoong.mohaeng.tts.dto.response.TTSResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TTSService {

    @Value("${openai.model}")
    private String apiModel;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final RestTemplate template;

    public TTSResponse requestTextAnalysis(String requestText) {
        TTSRequest request = TTSRequest.createTextRequest(apiModel, 300, "user", requestText);
        return template.postForObject(apiUrl,request,TTSResponse.class);
    }

    public TTSResponse requestImageAnalysis(MultipartFile image, String requestText) throws IOException {
        String base64Image = Base64.encodeBase64String(image.getBytes());
        String imageUrl = "data:image/jpeg;base64," + base64Image;
        TTSRequest request = TTSRequest.createImageRequest(apiModel, 300, "user", requestText, imageUrl);
        return template.postForObject(apiUrl, request, TTSResponse.class);
    }
}
