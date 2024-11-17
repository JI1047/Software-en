package hello.software_engineering.domain.airroute;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AirRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String airline;  // 필드 이름은 소문자로 시작
    private Integer price;
    private String departure;
    private String arrival;
    private String departureDay;
    private String arrivalDay;
    private LocalTime departure_time; // String -> LocalTime
    private LocalTime arrival_time;   // String -> LocalTime

    private String duration;
    public AirRoute(String airline, Integer price, String departure, String arrival,
                    String departureDay, String arrivalDay, LocalTime departureTime, LocalTime arrivalTime) {
        this.airline = airline;
        this.price = price;
        this.departure = departure;
        this.arrival = arrival;
        this.departureDay = departureDay;
        this.arrivalDay = arrivalDay;
        this.departure_time = departureTime;
        this.arrival_time = arrivalTime;
    }
}
