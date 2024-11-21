package hello.software_engineering.domain.group;


import hello.software_engineering.domain.member.Member;
import hello.software_engineering.domain.ticket.Ticket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_group") // 예약어 사용 방지
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupEmail;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    @OneToOne(mappedBy = "group") // 비주인 설정
    private Ticket ticket;

    public Group(Member member, String groupEmail) {
        this.members.add(member);
        this.groupEmail = member.getEmail();

    }
    public void addMember(Member member) {
        this.members.add(member);
        member.setGroup(this); // 양방향 관계 설정

        // 첫 번째 멤버의 이메일을 그룹 이메일로 설정
        if (this.groupEmail == null) {
            this.groupEmail = member.getEmail();
        }
    }
}
