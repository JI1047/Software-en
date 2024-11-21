package hello.software_engineering.service;


import hello.software_engineering.domain.airroute.AirRoute;
import hello.software_engineering.domain.ticket.Ticket;
import hello.software_engineering.repository.AirRouteRepository;
import hello.software_engineering.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final AirRouteRepository airRouteRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, AirRouteRepository airRouteRepository) {
        this.ticketRepository = ticketRepository;
        this.airRouteRepository = airRouteRepository;
    }

    public Long  save(Long airRouteId, String seatClass, int adultCount, int childCount, int totalPrice) {
        // AirRoute 객체 조회
        AirRoute airRoute = airRouteRepository.findById(airRouteId)
                .orElseThrow(() -> new IllegalArgumentException("AirRoute not found with ID: " + airRouteId));

        String pinCode=generatePinCode();
        // Ticket 생성 및 저장
        Ticket ticket = new Ticket();
        ticket.setAirRoute(airRoute);
        ticket.setSeatClass(seatClass);
        ticket.setAdultCount(adultCount);
        ticket.setChildCount(childCount);
        ticket.setTotalPrice(totalPrice);
        ticket.setPinCode(pinCode);

        Ticket savedTicket = ticketRepository.save(ticket);

        return savedTicket.getId();
    }

    public static String generatePinCode() {
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
    public Ticket findByGroupId(Long groupId) {
        return ticketRepository.findByGroupId(groupId);
    }
}
