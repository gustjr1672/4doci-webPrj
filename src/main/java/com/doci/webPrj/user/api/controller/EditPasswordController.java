package com.doci.webPrj.user.api.controller;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiEditPasswordController")
@RequestMapping("api/user")
public class EditPasswordController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberService memberService;

    @PostMapping("/password")
    public boolean isMatchNowPassword(@AuthenticationPrincipal MyUserDetails user,
                                      String nowPwd,
                                      String newPwd
                                      ){
        String password = user.getPassword();

        if (!passwordEncoder.matches(nowPwd ,password)) {
            return false;
        }
        String encodedPwd = passwordEncoder.encode(newPwd);
        memberService.updatePassword(user,encodedPwd);
        return true;
    }
}
