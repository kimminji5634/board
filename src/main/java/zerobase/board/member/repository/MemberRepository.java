package zerobase.board.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.board.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    // 구조만 만들어주면 JPA 가 자동으로 구현해줌
    Optional<Member> findByUserId(String userId);

    // Member 테이블에서 emailAuthKey 값을 주면 해당 되는 값 있나 찾아줌
    Optional<Member> findByEmailAuthKey(String emailAuthKey);

}
