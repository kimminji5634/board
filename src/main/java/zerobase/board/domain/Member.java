package zerobase.board.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data // getter, setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id값을 null로 하면 db가 알아서 자동 증가 해줌
    private Integer id; // memberInput에 없고 member에만 있음

    private String userId;
    private String name;
    private String email;
    private String password;
    private String phone;

    // 아래는 memberInput 에 없고 member 에만 있음
    // 회원가입 버튼을 누르면 랜덤키(인증번호)를 테이블에 저장
    private String emailAuthKey;
    // y인 경우에만 회원가입 되도록 한다. 이메일에서 링크 타고 오기 전 초깃값은 false 임
    private boolean emailAuthYn;
    // 이메일 인증한 날짜 => emailAuthYn 이 true가 될 때의 시간
    private LocalDateTime emailAuthDt;
    private Boolean adminYn;
    private LocalDateTime regDt;

}
