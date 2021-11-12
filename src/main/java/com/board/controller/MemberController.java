package com.board.controller;

import com.board.domain.MemberDTO;
import com.board.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    //회원가입
    @GetMapping("/user/signup")
    public String dispSignup() {
        return "/signup";
    }

    //회원가입 처리
    @PostMapping("/user/signup")
    public String execSignup(MemberDTO memberDTO) {
        memberService.joinUser(memberDTO);

        return "redirect:/user/login";
    }

    //로그인
    @GetMapping("/user/login")
    public String dispLogin() {
        return "/login";
    }

    //로그인 결과
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "/loginSuccess";
    }

    //로그아웃 결과
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "/logout";
    }

    //접근 거부
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "/denied";
    }

    //내정보
    @GetMapping("/user/info")
    public String dispMyInfo() {
        return "/myinfo";
    }

    //관리자
    @GetMapping("/admin")
    public String dispAdmin() {
        return "/admin";
    }
}
