package zerobase.board.components;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor // 생성자 주입
@Component
public class MailComponents {

    // 메일 보내기 위해 필요함, RequiredArgsConstructor 로 생성자 생성함
    private final JavaMailSender javaMailSender;

    // 줄바꿈 같은 기능을 시켜줌
    public boolean sendMail(String mail, String subject, String text) {

        boolean result = false;

        // 인터페이스기 때문에 바로 주입할 수 없다. 오버라이딩 해줘야 한다
        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper =
                        new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(mail);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(text, true);
            }
        };

        // io 에러가 날 수 있기 때문에 예외 처리
        try {
            javaMailSender.send(msg);
            result = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }


}

