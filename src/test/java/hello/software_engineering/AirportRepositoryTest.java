package hello.software_engineering;

import hello.software_engineering.domain.airport.Airport;
import hello.software_engineering.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class AirportRepositoryTest {

    @Autowired
    private AirportRepository airportRepository;

    @Test
    public void testGetAllAirports() {
        // 데이터베이스에서 공항 데이터를 모두 가져오기
        List<Airport> airports = airportRepository.findAll();

        // 리스트가 비어 있는지 확인
        if (airports.isEmpty()) {
            System.out.println("No airports found in the database.");
        } else {
            // 공항 리스트 출력
            System.out.println("Airports in database:");
            for (Airport airport : airports) {
                System.out.println("ID: " + airport.getId() + ", Name: " + airport.getName() + ", Code: " + airport.getCode());
            }
        }

        // 테스트 검증
        assertFalse(airports.isEmpty(), "Airport list should not be empty");
    }
}
