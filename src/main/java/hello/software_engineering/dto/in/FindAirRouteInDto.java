package hello.software_engineering.dto.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindAirRouteInDto {
    private String departure;
    private String arrival;
    private String departureDay;
}
