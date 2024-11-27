package hello.software_engineering.service;


import hello.software_engineering.domain.airroute.AirRoute;
import hello.software_engineering.domain.ticket.Ticket;
import hello.software_engineering.dto.in.CreateTicketInDto;
import hello.software_engineering.mapper.TicketMapper;
import hello.software_engineering.repository.AirRouteRepository;
import hello.software_engineering.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final AirRouteRepository airRouteRepository;

    private final TicketMapper ticketMapper = TicketMapper.INSTANCE;

    public Long  save(CreateTicketInDto inDto) {
        // AirRoute 객체 조회
        AirRoute airRoute = airRouteRepository.findById(inDto.getAirRouteId())
                .orElseThrow(() -> new IllegalArgumentException("AirRoute not found with ID: " + inDto.getAirRouteId()));

        // Ticket 생성 및 저장
        return ticketRepository.save(ticketMapper.dtoToEntity(airRoute, inDto)).getId();
    }

    public Ticket findByGroupId(Long groupId) {
        return ticketRepository.findByGroupId(groupId);
    }
}
