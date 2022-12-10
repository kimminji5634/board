package zerobase.board.member.domain;

import lombok.Data;
import lombok.ToString;

@ToString
@Data // getter, setter
public class MemberInput {

    private String userId;
    private String name;
    private String email;
    private String password;
    private String phone;

}
