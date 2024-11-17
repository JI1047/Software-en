package hello.software_engineering.repository;


import hello.software_engineering.domain.airroute.AirRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirRouteRepository extends JpaRepository<AirRoute, Long> {


    List<AirRoute> findByDepartureAndArrivalAndDepartureDay(String departure, String arrival, String departureDay);


    AirRoute findAirRouteById(Long id);


}
