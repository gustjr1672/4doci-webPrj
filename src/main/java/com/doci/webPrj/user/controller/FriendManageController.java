package com.doci.webPrj.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doci.webPrj.config.MyUserDetails;

@RequestMapping("friendmanage")
@Controller
public class FriendManageController {

    
    @GetMapping("main")
    public String main(
            Model model,
            @AuthenticationPrincipal MyUserDetails user) {
        return "friendmanage/main";
    }

}
