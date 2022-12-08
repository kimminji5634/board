package zerobase.board.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;

@ToString
@Data // getter, setter
public class MemberInput {

    private String userId;
    private String name;
    private String email;
    private String password;
    private String phone;

}
