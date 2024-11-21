package hello.software_engineering.dto.in;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupCreationRequest {
    private String groupEmail;
    private List<Long> membersIds;
}
