package hello.software_engineering.domain.member;


import hello.software_engineering.domain.group.Group;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    private String SN;
    private String name;
    private String gender;
    private String email;
    private String phone;
    private String birthdate;

    public Member(String name, String gender, String email ,String phone, String birthdate) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
    }



}
