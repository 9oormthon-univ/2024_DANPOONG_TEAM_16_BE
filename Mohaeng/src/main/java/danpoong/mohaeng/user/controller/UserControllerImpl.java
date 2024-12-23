package danpoong.mohaeng.user.controller;

import danpoong.mohaeng.user.dto.UserCourseRes;
import danpoong.mohaeng.user.dto.UserCreateReq;
import danpoong.mohaeng.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<String> userCreate(@RequestBody UserCreateReq userCreateReq) {
        if (!userService.createUser(userCreateReq.getUuid()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 유저입니다.\n");

        return ResponseEntity.status(HttpStatus.CREATED).body("유저 정보를 생성했습니다.\n");
    }

    @GetMapping("/exist")
    public ResponseEntity<String> userExist(@RequestParam("uuid") String uuid) {
        if (userService.checkUserExist(uuid))
            return ResponseEntity.status(HttpStatus.OK).body("유저가 존재합니다.\n");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유저가 존재하지 않습니다.\n");
    }

    @GetMapping("/{uuid}/course")
    public ResponseEntity<List<UserCourseRes>> getUserCourses(@PathVariable("uuid") String uuid){
        if (!userService.checkUserExist(uuid))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(userService.getCourseList(uuid));
    }
}
