package hello.software_engineering.service;

import hello.software_engineering.domain.member.Member;
import hello.software_engineering.repository.MemberRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member save(Member member) {

        return memberRepository.save(member);
    }




}
