package danpoong.mohaeng.user.controller;

import danpoong.mohaeng.user.dto.UserCreateReq;
import danpoong.mohaeng.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<String> userCreate(@RequestBody UserCreateReq userCreateReq) {
        if (!userService.createUser(userCreateReq.getUuid()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 유저입니다.\n");

        return ResponseEntity.status(HttpStatus.CREATED).body("유저 정보를 생성했습니다.\n");
    }
}
