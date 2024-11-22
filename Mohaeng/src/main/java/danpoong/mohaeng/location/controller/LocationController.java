package danpoong.mohaeng.location.controller;

import danpoong.mohaeng.location.dto.LocationInfoRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface LocationController {
    @Operation(summary = "관광지 상세 정보 조회 API", description = "contentId를 기반으로 관광지 상세 정보를 조회하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관광지 상세 정보를 리턴합니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "관광지 정보를 조회할 수 없습니다.", content = @Content(mediaType = "application/json")),
    })
    @Parameter(name = "contentId", description = "관광지 고유 ID")
    public ResponseEntity<LocationInfoRes> getInfo(@PathVariable("contentId") Long contentId);
}
