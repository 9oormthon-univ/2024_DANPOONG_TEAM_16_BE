package danpoong.mohaeng.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "uuid = 유저 고유 ID")
public class UserCreateReq {
    private String uuid;
}
