package com.doci.webPrj.user.api.controller;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("MemberController")
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @PutMapping("nickname")
    public String editNickname(@AuthenticationPrincipal MyUserDetails user, String nickname){
        memberService.updateNickname(user,nickname);
        return "";
    }


}
