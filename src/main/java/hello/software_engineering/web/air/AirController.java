package hello.software_engineering.web.air;

import hello.software_engineering.domain.airport.Airport;
import hello.software_engineering.domain.airroute.AirRoute;
import hello.software_engineering.domain.seat.SeatClass;
import hello.software_engineering.dto.in.FindAirRouteInDto;
import hello.software_engineering.repository.AirRouteRepository;
import hello.software_engineering.service.AirRouteService;
import hello.software_engineering.service.AirportService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/air")
@RequiredArgsConstructor
public class AirController {

    private final AirportService airportService;
    private final AirRouteService airRouteService;
    @GetMapping
    public String home(Model model) {
        List<Airport> airports = airportService.getAllAirports();
        model.addAttribute("airports", airports);
        return "index";  // src/main/resources/static/index.html로 연결됨
    }

    @GetMapping("/Search")
    public String Search() {
        return "basic/Search";
    }

    @GetMapping("/Result")
    public String Result(@RequestParam String departure, @RequestParam String arrival,
                         @RequestParam String departure_day,
                         Model model) {
        FindAirRouteInDto inDto = FindAirRouteInDto.builder()
                .departure(departure)
                .arrival(arrival)
                .departureDay(departure_day)
                .build();

        List<AirRoute> matchingRoutes = airRouteService.findByRoute(inDto);
        if (!matchingRoutes.isEmpty()) {
            model.addAttribute("firstRoute", matchingRoutes.get(0)); // 첫 번째 요소 추가
        }
        model.addAttribute("routes", matchingRoutes);
        return "basic/Result";
    }
    @GetMapping("/{id}")
    public String airLine(@PathVariable Long id, Model model) {
        AirRoute findAirRoute = airRouteService.findById(id);
        model.addAttribute("route", findAirRoute);
        model.addAttribute("seatClass", SeatClass.values());
        return "basic/Airline";
    }




}
