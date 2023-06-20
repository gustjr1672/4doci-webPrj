package com.doci.webPrj.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.service.NotificationService;

@Controller("userMainController")
public class MainController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("main")
    public String main(Model model, @AuthenticationPrincipal MyUserDetails user) {

        List<Member> requestMemberList = notificationService.getRequest(user.getId());
        System.out.println(requestMemberList);
        model.addAttribute("requestMemberList", requestMemberList);
        return "user/main";
    }

}
