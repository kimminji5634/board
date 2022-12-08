package zerobase.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import zerobase.board.domain.Member;
import zerobase.board.domain.MemberInput;
import zerobase.board.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RequiredArgsConstructor // 생성자 주입
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/member/register")
    public String registerSubmit(Model model,
                                 MemberInput parameter) {

        boolean result = memberService.register(parameter);
        model.addAttribute("result", result);

        return "member/register_complete";
    }

    @GetMapping("/member/login")
    public String login() {

        return "member/login";
    }

    @PostMapping("/member/login") // post는 버튼 클릭시 입력받은 데이터를 서버로 전달
    public String loginSubmit(Model model, // model 쓰기 위해 model 주입 받는다
                              MemberInput parameter) {

        boolean result = memberService.login(parameter);
        model.addAttribute("result", result);

        return "member/login_complete";
    }

    @GetMapping("/member/email-auth")
    public String emailAuth(Model model, HttpServletRequest request) {

        // 'http://localhost:8080/member/email-auth?id=" + uuid + "'
        String uuid = request.getParameter("id"); // id

        // => 웹 주소에 http://localhost:8080/member/email-auth?id=123456
        // 아래를 쓰면 콘솔에 123456인 id가 잘 찍히나 확인하기 위해 해줌
        // System.out.println(uuid);

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "member/email_auth";
    }
}
