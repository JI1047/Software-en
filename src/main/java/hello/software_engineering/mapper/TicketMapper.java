package hello.software_engineering.mapper;

import hello.software_engineering.domain.airroute.AirRoute;
import hello.software_engineering.domain.ticket.Ticket;
import hello.software_engineering.dto.in.CreateTicketInDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Random;

@Mapper
public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    default Ticket dtoToEntity(AirRoute airRoute, CreateTicketInDto inDto){
        Ticket ticket = new Ticket();
        ticket.setAirRoute(airRoute);
        ticket.setSeatClass(inDto.getSeatClass());
        ticket.setAdultCount(inDto.getAdultCount());
        ticket.setChildCount(inDto.getChildCount());
        ticket.setTotalPrice(inDto.getTotalPrice());
        ticket.setPinCode(generatePinCode());
        return ticket;
    }

    private String generatePinCode() {
        // 핀코드에 사용할 문자 집합 (영어 대소문자 + 숫자)
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder pinCode = new StringBuilder();

        // 6자리 핀코드 생성
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            pinCode.append(characters.charAt(index));
        }

        return pinCode.toString();
    }
}
