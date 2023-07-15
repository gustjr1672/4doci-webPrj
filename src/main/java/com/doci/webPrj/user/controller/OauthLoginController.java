package com.doci.webPrj.user.controller;

import com.doci.webPrj.config.MyUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class OauthLoginController {

    @GetMapping("/register/nickname")
    public void setNickName(@AuthenticationPrincipal MyUserDetails user) {

        System.out.println("user = " + user.getRoles());
    }
}
