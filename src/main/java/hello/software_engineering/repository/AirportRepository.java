package hello.software_engineering.repository;

import hello.software_engineering.domain.airport.Airport;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AirportRepository extends JpaRepository<Airport, Long> {


}