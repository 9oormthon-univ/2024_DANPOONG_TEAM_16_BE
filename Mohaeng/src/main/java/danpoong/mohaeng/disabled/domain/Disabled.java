package danpoong.mohaeng.disabled.domain;

import danpoong.mohaeng.location.domain.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "disabled")
@Getter @Setter
@NoArgsConstructor
public class Disabled {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // disabled_number는 자동 생성
    @Column(name = "number")
    private Long number;  // 장애시설코드

    @OneToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Location location;

    @Column(name = "parking")
    private String parking;       // 주차여부

    @Column(name = "route")
    private String route;         // 대중교통

    @Column(name = "public_transport")
    private String publicTransport; // 접근로

    @Column(name = "ticket_office")
    private String ticketOffice;  // 매표소

    @Column(name = "promotion")
    private String promotion;     // 홍보물

    @Column(name = "wheelchair")
    private String wheelchair;   // 휠체어

    @Column(name = "entrance")
    private String entrance;          // 출입통로

    @Column(name = "elevator")
    private String elevator;      // 엘리베이터

    @Column(name = "restroom")
    private String restroom;      // 화장실

    @Column(name = "auditorium")
    private String auditorium;    // 관람석

    @Column(name = "room")
    private String room;          // 객실

    @Column(name = "handicap_etc")
    private String handicapEtc;   // 지체장애 기타상세

    @Column(name = "braile_block")
    private String braileBlock;   // 점자블록

    @Column(name = "help_dog")
    private String helpDog;       // 보조견동반

    @Column(name = "guide_human")
    private String guideHuman;    // 안내요원

    @Column(name = "audio_guide")
    private String audioGuide;    // 오디오가이드

    @Column(name = "big_print")
    private String bigPrint;      // 큰활자 홍보물

    @Column(name = "braile_promotion")
    private String brailePromotion; // 점자홍보물 및 점자표지판

    @Column(name = "guide_system")
    private String guideSystem;   // 유도안내설비

    @Column(name = "blind_handicap_etc")
    private String blindHandicapEtc; // 시각장애 기타상세

    @Column(name = "sign_guide")
    private String signGuide;     // 수화안내

    @Column(name = "video_guide")
    private String videoGuide;    // 자막 비디오가이드 및 영상자막안내

    @Column(name = "hearing_room")
    private String hearingRoom;   // 청각장애 객실

    @Column(name = "hearing_handicap_etc")
    private String hearingHandicapEtc; // 청각 장애 기타 상세

    @Column(name = "stroller")
    private String stroller;      // 유모차

    @Column(name = "lactation_room")
    private String lactationRoom; // 수유실

    @Column(name = "baby_spare_chair")
    private String babySpareChair; // 유아용보조의자

    @Column(name = "infants_family_etc")
    private String infantsFamilyEtc; // 영유아가족 기타상세
}
