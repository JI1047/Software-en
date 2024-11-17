package hello.software_engineering.domain.airport;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor // 파라미터 없는 기본 생성자를 추가
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;

    // 파라미터가 있는 생성자 추가
    public Airport(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
