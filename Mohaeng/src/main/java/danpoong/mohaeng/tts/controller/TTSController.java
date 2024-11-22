package danpoong.mohaeng.tts.controller;

import danpoong.mohaeng.tts.dto.response.TTSResponse;
import danpoong.mohaeng.tts.service.TTSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TTSController {
    private final TTSService TTSservice;

    @PostMapping("/image")
    public String imageAnalysis(@RequestParam MultipartFile image)
            throws IOException {
        String fixedRequestText = "이 화면에 대한 설명으로 TTS 서비스를 할거야 시각장애인에게 설명한다고 생각하고 이거를 고려해서 화면에 대한 설명 200자 이내로 해줘";

        TTSResponse response = TTSservice.requestImageAnalysis(image, fixedRequestText);
        return response.getChoices().get(0).getMessage().getContent();
    }

    @PostMapping("/text")
    public String textAnalysis(@RequestParam String requestText) {
        TTSResponse response = TTSservice.requestTextAnalysis(requestText);
        return response.getChoices().get(0).getMessage().getContent();
    }
}
