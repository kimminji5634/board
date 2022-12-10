package zerobase.board.member.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import zerobase.board.member.domain.MemberInput;

public interface MemberService extends UserDetailsService {

    boolean login(MemberInput parameter);

    boolean register(MemberInput parameter);

    // uuid 에 해당하는 계정을 활성화 함
    boolean emailAuth(String uuid);

}
