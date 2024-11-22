package danpoong.mohaeng.location.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import danpoong.mohaeng.area.domain.Area;
import danpoong.mohaeng.course.domain.UserCourse;
import danpoong.mohaeng.disabled.domain.Disabled;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Entity
@Table(name = "location")
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)  // 정의되지 않은 필드 무시
@ToString
public class Location {

    @Id
    @JsonProperty("contentid")
    @Column(name = "content_id")
    private Long contentId;   // 콘텐츠 ID

    @ManyToOne
    @JoinColumn(name = "area_code")
    private Area area;        // 외래 키 (Area와의 관계)

    @OneToOne(mappedBy = "location")
    private Disabled disabled;

    @OneToMany(mappedBy = "location")
    List<UserCourse> userCourseList;

    @JsonProperty("contenttypeid")
    @Column(name = "content_type_id")
    private Long contentTypeId;  // 관광 타입 ID

    @JsonProperty("title")
    @Column(name = "content_title")
    private String contentTitle; // 콘텐츠 제목

    @JsonProperty("addr1")
    @Column(name = "addr")
    private String addr;         // 주소

    @JsonProperty("addr2")
    @Column(name = "detail_addr")
    private String detailAddr;   // 상세 주소

    @JsonProperty("mapx")
    @Column(name = "gps_x")
    private Double gpsX;         // GPS X 좌표

    @JsonProperty("mapy")
    @Column(name = "gps_y")
    private Double gpsY;         // GPS Y 좌표

    @Column(name = "description")
    private String description;  // 개요

    @Column(name = "phone_number")
    private String phoneNumber;  // 전화번호

    @JsonProperty("firstimage")
    @Column(name = "original_image")
    private String originalImage; // 원본 이미지

    @JsonProperty("firstimage2")
    @Column(name = "thumbnail_image")
    private String thumbnailImage; // 썸네일 이미지

    public String toPrompt() {
        return "[contentId : " + getContentId()
                + ", contentTypeId : " + getContentTypeId()
                + ", description : " + getDescription()
                + ", gpsX : " + getGpsX()
                + ", gpsY : " + getGpsY()
                + ", addr : " + getAddr() + "]";
    }
}
