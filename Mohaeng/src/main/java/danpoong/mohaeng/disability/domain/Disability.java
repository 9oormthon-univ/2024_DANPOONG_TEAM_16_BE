package danpoong.mohaeng.disability.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Disability {
    SENIOR("senior"),
    WHEELCHAIR("wheelchair"),
    BLIND("blind"),
    HEARING("hearing"),
    INFANTS("infants"),
    NONE("none");

    private final String name;
}