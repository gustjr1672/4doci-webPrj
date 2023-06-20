package com.doci.webPrj.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.service.NotificationService;

@Controller
@RequestMapping("notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    // @GetMapping("request/accept")
    // public String requestAccept(
    // Model model,
    // @AuthenticationPrincipal MyUserDetails user,
    // @RequestParam(name = "id") int memberId) {
    // notificationService.requestAccept(user.getId(), memberId);
    // List<Member> requestMemberList =
    // notificationService.getRequest(user.getId());
    // model.addAttribute("name", user.getName());
    // model.addAttribute("requestMemberList", requestMemberList);
    // return "/user/main";
    // }

    // @GetMapping("request/refuse")
    // public String requestRefuse(
    // Model model,
    // @AuthenticationPrincipal MyUserDetails user,
    // @RequestParam(name = "id") int memberId) {
    // notificationService.requestRefuse(user.getId(), memberId);
    // List<Member> requestMemberList =
    // notificationService.getRequest(user.getId());
    // model.addAttribute("name", user.getName());
    // model.addAttribute("requestMemberList", requestMemberList);
    // return "/user/main";
    // }
}
