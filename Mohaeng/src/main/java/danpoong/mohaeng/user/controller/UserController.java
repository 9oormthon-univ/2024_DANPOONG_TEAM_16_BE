package danpoong.mohaeng.user.controller;

import danpoong.mohaeng.user.dto.UserCourseRes;
import danpoong.mohaeng.user.dto.UserCreateReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "User", description = "유저 관련 API")
public interface UserController{

    @Operation(summary = "유저 생성 API", description = "uuid 기반으로 유저 정보를 생성하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "유저 정보를 생성했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 유저입니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> userCreate(@RequestBody UserCreateReq userCreateReq);

    @Operation(summary = "유저 존재 확인 API", description = "uuid 기반으로 유저 존재 여부를 확인하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저가 존재합니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유저가 존재하지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameter(name = "uuid", description = "유저 고유 ID")
    public ResponseEntity<String> userExist(@RequestParam("uuid") String uuid);

    @Operation(summary = "유저 코스 목록 조회 API", description = "uuid 기반으로 유저 코스 목록을 조회하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 코스 목록을 리턴합니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저입니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameter(name = "uuid", description = "유저 고유 ID")
    public ResponseEntity<List<UserCourseRes>> getUserCourses(@PathVariable("uuid") String uuid);
}
