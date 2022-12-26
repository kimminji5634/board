package zerobase.board.member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import zerobase.board.components.MailComponents;
import zerobase.board.error.MemberNotEmailAuthException;
import zerobase.board.member.domain.MemberInput;
import zerobase.board.member.entity.Member;
import zerobase.board.member.repository.MemberRepository;
import zerobase.board.member.util.PasswordUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor // 생성자 주입
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MailComponents mailComponents; // 메일 보내기 위해 필요함



    // 회원가입시 db에 정보 저장
    public boolean register(MemberInput parameter) {

        // 동일한 아이디로 회원가입 못함 => return false
        Optional<Member> optionalMember =
                memberRepository.findByUserId(parameter.getUserId());
        if (optionalMember.isPresent()) {
            return false;
        }

        String uuid = UUID.randomUUID().toString();
        // BCrypt 암호화를 쓰려면 build.gradle 에서 spring.security 를 주입받아야 함
        String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());

        Member member = Member.builder()
                .userId(parameter.getUserId())
                .name(parameter.getName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .email(parameter.getEmail())
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .adminYn(false)
                .build();

        memberRepository.save(member);

        String email = parameter.getEmail();
        String subject = "BOARD 사이트에 가입을 축하드립니다.";
        String text = "<p>BOARD 사이트에 가입을 축하드립니다.</p>"
                + "<p>아래 링크를 클릭하시면 가입이 완료됩니다.</p>"
                + "<div><a href='http://localhost:8080/member/"
                + "email-auth?id=" + uuid + "'>가입 완료</a></div>";
        mailComponents.sendMail(email, subject, text);

        return true;
    }

    // 이메일에 준 링크 타고 오면 EmailAuthYn true로 바꿔줌 => EmailAuthYn이 true 여야만 login 됨
    public boolean emailAuth(String uuid) {

        // findById(id) 가 아니므로 repository 에서 메소드를 하나 생성해줘야 함
        // 테이블에 있나 찾아줘
        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if (!optionalMember.isPresent()) {
            return false;
        }

        // else 경우
        Member member = optionalMember.get();
        member.setEmailAuthYn(true); // false -> true
        member.setEmailAuthDt(LocalDateTime.now()); // true 로 변경될 때의 시간을 저장
        memberRepository.save(member);

        return true;
    }

    // 아이디와 비밀번호 똑같이 입력한 경우에만 로그인 됨!!!
    public boolean login(MemberInput parameter) {

        Optional<Member> optionalMember = memberRepository.findByUserId(parameter.getUserId());
        Member optMember = optionalMember.get();

        if (!optMember.isEmailAuthYn()) { // false 인 경우
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 해주세요.");
        }

        if (!PasswordUtils.equals(parameter.getPassword(), optMember.getPassword())){
            return false;
        }

        return true;
    }


    // SpringSecurity 에서 제공해줌, UserDetails 도 원래 제공됨
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findByUserId(userId);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (member.isAdminYn()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // User 사용하려면 username, password, row 던져야함, User 들어가면 알 수 있음
        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }
}
