package danpoong.mohaeng.course.controller;

import danpoong.mohaeng.course.dto.CourseCreateReq;
import danpoong.mohaeng.course.dto.CourseCreateRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Course", description = "코스 관련 API")
public interface CourseController {
    @Operation(summary = "코스 등록 API", description = "AI 추천 코스를 코스 등록하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "코스 정보를 생성했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 코스 정보입니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<CourseCreateRes> courseCreate(@RequestBody CourseCreateReq courseCreateReq);
}
