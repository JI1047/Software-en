package hello.software_engineering.service;


import hello.software_engineering.domain.group.Group;
import hello.software_engineering.domain.member.Member;
import hello.software_engineering.dto.in.GroupCreationRequest;
import hello.software_engineering.repository.GroupRepository;
import hello.software_engineering.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, MemberRepository memberRepository) {
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;

    }


    public Group createGroup(GroupCreationRequest request) {
        Group group = new Group();
        group.setGroupEmail(request.getGroupEmail());

        List<Member> members = memberRepository.findAllById(request.getMembersIds());

        for (Member member : members) {
            group.addMember(member);
        }

        // 그룹 저장
        return groupRepository.save(group);
    }
}
