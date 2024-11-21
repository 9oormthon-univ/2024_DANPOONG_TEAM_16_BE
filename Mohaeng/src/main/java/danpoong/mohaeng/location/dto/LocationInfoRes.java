package danpoong.mohaeng.location.dto;

import danpoong.mohaeng.disabled.domain.Disabled;
import danpoong.mohaeng.location.domain.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocationInfoRes {
    //location 관련 컬럼
    private Long contentId;
    private String contentTitle;
    private String addr;
    private Double gpsX;
    private Double gpsY;
    private String description;
    private String originalImage;
    private String thumbnailImage;

    //Disabled 관련 컬럼
    //휠체어 사용자 && 노약자
    private String publicTransport;
    private String elevator;
    private String restroom;
    private String wheelchair;

    //시각 장애인
    private String helpDog;
    private String guideHuman;
    private String braileBlock;

    //청각 장애인
    private String signGuide;
    private String videoGuide;
    private String hearingHandicapEtc;

    //영유아
    private String stroller;
    private String lactationRoom;
    private String babySpareChair;

    public static LocationInfoRes infoBuilder(Location location, Disabled disabled) {
        return LocationInfoRes.builder()
                .contentId(location.getContentId())
                .contentTitle(location.getContentTitle())
                .addr(location.getAddr())
                .gpsX(location.getGpsX())
                .gpsY(location.getGpsY())
                .description(location.getDescription())
                .originalImage(location.getOriginalImage())
                .thumbnailImage(location.getThumbnailImage())
                .publicTransport(disabled.getPublicTransport())
                .elevator(disabled.getElevator())
                .restroom(disabled.getRestroom())
                .wheelchair(disabled.getWheelchair())
                .helpDog(disabled.getHelpDog())
                .guideHuman(disabled.getGuideHuman())
                .braileBlock(disabled.getBraileBlock())
                .signGuide(disabled.getSignGuide())
                .videoGuide(disabled.getVideoGuide())
                .hearingHandicapEtc(disabled.getHearingHandicapEtc())
                .stroller(disabled.getStroller())
                .lactationRoom(disabled.getLactationRoom())
                .babySpareChair(disabled.getBabySpareChair())
                .build();
    }
}
