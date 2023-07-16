package com.doci.webPrj.common.controller;

import com.doci.webPrj.common.service.JoinFormService;
import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Autowired
    MemberService memberService;
    @Autowired
    JoinFormService joinFormService;
    @GetMapping("/")
    public String login(){
        return "common/login";
    }

    @GetMapping("/welcome")
    public String snsLoginWelcome(){

        return "/common/welcome";
    }

    @PostMapping("/register/snsLogin")
    public String registerNickname(@AuthenticationPrincipal MyUserDetails user, String nickname){
        Member member = Member.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .nickname(nickname)
                .roles("USER").build();
        joinFormService.join(member);

        return "redirect:/";
    }
}
