package danpoong.mohaeng.tts.controller;

import danpoong.mohaeng.tts.dto.response.TTSResponse;
import danpoong.mohaeng.tts.service.TTSService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "TTS", description = "TTS 관련 API")
public class TTSController {
    private final TTSService TTSservice;

    @Operation(
            summary = "이미지 전송 API",
            description = "이미지를 GPT-4o 모델에게 전송하고 시각장애인을 위한 200자 이내 화면 설명을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 설명 생성", content = @Content(mediaType = "application/json")),
            }
    )
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String imageAnalysis(@RequestParam(name = "image")
                                    @Parameter(description = "분석할 이미지 파일 (form-data의 key 값: 'image')", required = true)
                                    MultipartFile image)
            throws IOException {
        String fixedRequestText = "이 화면에 대한 설명으로 TTS 서비스를 할거야 시각장애인에게 설명한다고 생각하고 이거를 고려해서 화면에 대한 설명 200자 이내로 해줘";

        TTSResponse response = TTSservice.requestImageAnalysis(image, fixedRequestText);
        return response.getChoices().get(0).getMessage().getContent();
    }

}
