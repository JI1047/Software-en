package hello.software_engineering.service.implement;

import hello.software_engineering.domain.airroute.AirRoute;
import hello.software_engineering.dto.in.FindAirRouteInDto;
import hello.software_engineering.repository.AirRouteRepository;
import hello.software_engineering.service.AirRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirRouteServiceImpl implements AirRouteService {

    private final AirRouteRepository airRouteRepository;

    // 모든 항공편 조회
    @Override
    public List<AirRoute> findAll() {
        return airRouteRepository.findAll();
    }

    // 출발지와 도착지, 출발 날짜에 따른 항공편 조회
    @Override
    public List<AirRoute> findByRoute(FindAirRouteInDto inDto) {
        List<AirRoute> routes = airRouteRepository.findByDepartureAndArrivalAndDepartureDay(inDto.getDeparture(), inDto.getArrival(), inDto.getDepartureDay());

        // 각 항공편에 대해 소요 시간 계산
        routes.forEach(route -> route.setDuration(calculateDuration(route.getDepartureTime(), route.getArrivalTime())));

        return routes;
    }

    @Override
    public AirRoute findById(Long id) {
        return airRouteRepository.findAirRouteById(id);
    }

    private String calculateDuration(LocalTime departureTime, LocalTime arrivalTime) {
        Duration duration = Duration.between(departureTime, arrivalTime);
        if (duration.isNegative()) {
            // 자정을 넘는 경우
            duration = duration.plusHours(24);
        }
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return hours + "시간 " + minutes + "분";
    }








}