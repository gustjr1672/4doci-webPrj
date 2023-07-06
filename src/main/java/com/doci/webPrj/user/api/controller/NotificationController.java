package com.doci.webPrj.user.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.InvitationNotification;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.service.InvitationService;
import com.doci.webPrj.user.service.NotificationService;

@RestController("apiNotificationController")
@RequestMapping("notifications")
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    @Autowired
    InvitationService invitationService;
    @GetMapping("request")
    public List<Member> request(@AuthenticationPrincipal MyUserDetails user) {
        return notificationService.getRequest(user.getId());
    }

    @PostMapping("request/{memberId}")
    public List<Member> add(
            Model model,
            @AuthenticationPrincipal MyUserDetails user,
            @PathVariable int memberId) {
        notificationService.requestAccept(user.getId(), memberId);
        List<Member> requestMemberList = notificationService.getRequest(user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("requestMemberList", requestMemberList);
        return requestMemberList;
    }

    @DeleteMapping("request/{memberId}")
    public List<Member> delete(
            Model model,
            @AuthenticationPrincipal MyUserDetails user,
            @PathVariable int memberId) {
        notificationService.requestRefuse(user.getId(), memberId);
        List<Member> requestMemberList = notificationService.getRequest(user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("requestMemberList", requestMemberList);
        return requestMemberList;
    }

    @GetMapping("modal")
    public Map<String, List<Member>> modal() {

        return null;
    }

    @GetMapping("invite")
    public List<InvitationNotification> invite(@AuthenticationPrincipal MyUserDetails user){
        return invitationService.getInvite(user.getId());
    }
}
