package hello.software_engineering.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTicketInDto {
    private Long airRouteId;
    private String seatClass;
    private Integer adultCount;
    private Integer childCount;
    private Integer totalPrice;
}

