package danpoong.mohaeng.trip_type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TripType {
    FOREST("forest"),
    OCEAN("ocean"),
    CULTURE("culture"),
    OUTSIDE("outside");

    private final String name;
}
