package hello.software_engineering.service;

import hello.software_engineering.domain.airroute.AirRoute;
import hello.software_engineering.dto.in.FindAirRouteInDto;

import java.util.List;

public interface AirRouteService {
    List<AirRoute> findAll();
    List<AirRoute> findByRoute(FindAirRouteInDto inDto);
    AirRoute findById(Long id);

}
