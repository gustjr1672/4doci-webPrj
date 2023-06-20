package com.doci.webPrj.user.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.service.NotificationService;

@RestController("apiNotificationController")
@RequestMapping("notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("request/accept")
    public List<Member> requestAccept(
            Model model,
            @AuthenticationPrincipal MyUserDetails user,
            @RequestParam(name = "id") int memberId) {
        notificationService.requestAccept(user.getId(), memberId);
        List<Member> requestMemberList = notificationService.getRequest(user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("requestMemberList", requestMemberList);
        return requestMemberList;
    }

    @GetMapping("request/refuse")
    public List<Member> requestRefuse(
            Model model,
            @AuthenticationPrincipal MyUserDetails user,
            @RequestParam(name = "id") int memberId) {
        notificationService.requestRefuse(user.getId(), memberId);
        List<Member> requestMemberList = notificationService.getRequest(user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("requestMemberList", requestMemberList);
        return requestMemberList;
    }
}
