package zerobase.board.controller;

import zerobase.board.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final MailComponents mailComponents;

   /* 메일이 잘 보내지는지 시험해봄 => 이메일을 지정해줌
      MemberService에서 구현한 건 회원가입시 입력한 이메일로 uuid 넣은 링크를 쏴줌
    @RequestMapping ("/") // 요청에 대한 매핑을 해주겠다
    public String index() {

        String email = "kimminji5634@gmail.com";
        String subject = "안녕하세요. 제로베이스 입니다.";
        String text = "<p>안녕하세요.</p><p>반갑습니다.</p>";

        //mailComponents.sendMailTest();
        mailComponents.sendMail(email, subject, text);

        return "index"; // index.html 파일을 리턴함
    }*/
}

