package hello.software_engineering.domain.ticket;

import hello.software_engineering.domain.airroute.AirRoute;
import hello.software_engineering.domain.group.Group;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "air_route_id", nullable = false) // 외래 키 이름 설정
    private AirRoute airRoute; // 항공 경로 객체

    @OneToOne
    @JoinColumn(name = "group_id") // 주인 설정
    private Group group;

    private int adultCount;  // 성인 수
    private int childCount; // 유아 수

    private int totalPrice; // 가격


    private String seatClass; // 좌석 등급

    private String pinCode; // 핀 번호

    public Ticket(AirRoute airRoute,  int adultCount, int childCount, int totalPrice, String seatClass, String pinCode) {
        this.airRoute = airRoute;
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.totalPrice = totalPrice;
        this.seatClass = seatClass;
        this.pinCode = pinCode;
    }


}
