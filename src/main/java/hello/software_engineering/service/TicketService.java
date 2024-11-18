package hello.software_engineering.service;


import hello.software_engineering.domain.airroute.AirRoute;
import hello.software_engineering.domain.ticket.Ticket;
import hello.software_engineering.repository.AirRouteRepository;
import hello.software_engineering.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final AirRouteRepository airRouteRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, AirRouteRepository airRouteRepository) {
        this.ticketRepository = ticketRepository;
        this.airRouteRepository = airRouteRepository;
    }

    public void save(Long airRouteId, String seatClass, int adultCount, int childCount, int totalPrice) {
        // AirRoute 객체 조회
        AirRoute airRoute = airRouteRepository.findById(airRouteId)
                .orElseThrow(() -> new IllegalArgumentException("AirRoute not found with ID: " + airRouteId));

        // Ticket 생성 및 저장
        Ticket ticket = new Ticket(airRoute,  adultCount, childCount, totalPrice, seatClass, null);
        ticketRepository.save(ticket);
    }
}
