package zerobase.board.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import zerobase.board.member.service.MemberService;

@EnableWebSecurity
@Configuration // config 에 관련된 파일임
// 어노테이션을 붙인다고 기능이 실행되는게 아니라 상속을 받아야 함
public class SecurityConfiguration {
    // 상속을 받고 원하는 부분을 오버라이드로 구현 *ctrl + o 누르면 오버라이드 할 수 있는 메서드 나옴
    // WebSecurityConfigurerAdapter_configure(http:HttpSecurity, web:WebSecurity) 두 개 오버라이드

    private final MemberService memberService;
    AuthenticationManager authenticationManager;

    public SecurityConfiguration(MemberService memberService) {
        this.memberService = memberService;
    }


    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // UserAuthenticationFailureHandler 가져오기
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin(); /*editor 적용됨*/

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(memberService);
        authenticationManager = authenticationManagerBuilder.build();

        http.authenticationManager(authenticationManager);

        // 페이지 권한 설정
        http.authorizeRequests()
                // 컨트롤러 주소 쓰는거임!!!
                .antMatchers("/", // index 페이지
                        "/member/register", // 회원가입
                        "/member/login", //로그인 => 얘 안쓰고 들어가려 하면 404에러
                        "/member/login_complete",
                        // email-auth를 email_auth로 쳐서 계속 에러떴었음... 하
                        "/member/email-auth",
                        "/member/find/password",
                        "/member/reset/password",
                        "/board/**"
                )
                // logout 페이지도 제공하는데 로그아웃 후 어느 주소로도 다 들어가는 것 확인 가능
                .permitAll(); // 접근 가능하게 하겠다
        //** 다른 페이지들은 로그인해야 접근이 가능함!!

        // admin 부분...
        http.authorizeRequests()
                .antMatchers("/**")
                .hasAuthority("ROLE_ADMIN");

        http.formLogin()
                .loginPage("/board")
                .usernameParameter("userId") // login.html에 써준 name = userId 가 맞다
                .passwordParameter("password")
                .loginProcessingUrl("/login")  //로그인 form
                .defaultSuccessUrl("/member/login_complete") // 로그인 성공 후 바로 뜨는 페이지
                .failureUrl("/member/login_complete");


        // member/logout 주소에서 로그아웃 성공되면 루트(/)로 이동하고 세션을 초기화한다~!!
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

        http.exceptionHandling()
                .accessDeniedPage("/error/denied");

        return http.build();
    }



    // USER_ROLE, ADMIN_ROLE MemberService에서 정해주는데 필요

}

