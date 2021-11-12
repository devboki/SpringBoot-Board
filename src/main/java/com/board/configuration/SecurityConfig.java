package com.board.configuration;

import com.board.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //security 설정 클래스 선언 어노테이션. 일반적으로 WebSecurityConfigurerAdapter 상속받아 메서드 구현
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder(){ //spring security 에서 제공하는 비밀번호 암호화 객체
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //resources/static 디렉토리 하위 파일들은 인증 무시하고 항상 통과하도록 설정
        web.ignoring().antMatchers("/css/**", "/fonts/**", "/plugin/**", "/scripts/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //페이지 권한 설정 : matchers - 경로, role - 권한
                //모든 경로에 대해서는 권한없이 접근 가능
                //user/myinfo 는 회원만, admin/**은 관리자만 접근
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/myinfo").hasRole("MEMBER")
                .antMatchers("/**").permitAll()
            .and()
                //로그인 설정 : spring security 에서 제공하는 로그인 폼 사용
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/user/login/result")
                //.usernameParameter("파라미터명")
                //로그인폼 input 기본 name=username 이지만 바꾸고 싶다면 지정해줄 수 있음
                .permitAll()
            .and()
                //로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) //기본 url(/logout)이 아닌 경로 지정
                .logoutSuccessUrl("/user/logout/result")
                .invalidateHttpSession(true) //http 세션 초기화
                //.deleteCookies("KEY명") 로그아웃시 특정 쿠키를 제고하고 싶을 때 사용
            .and()
                //403 예외처리 핸들링. 접근 권한이 없을 때 로그인 페이지로 이동
                .exceptionHandling().accessDeniedPage("/user/denied");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
