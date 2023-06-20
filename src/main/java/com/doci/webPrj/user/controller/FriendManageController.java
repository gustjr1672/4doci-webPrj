package com.doci.webPrj.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.service.FriendManageService;
import com.doci.webPrj.user.service.NotificationService;

@RequestMapping("friendmanage")
@Controller
public class FriendManageController {

    @Autowired
    FriendManageService friendManageService;
    @Autowired
    NotificationService notificationService;

    @GetMapping("main")
    public String main(
            Model model,
            @AuthenticationPrincipal MyUserDetails user) {
        return "user/friendmanage/main";
    }

    @GetMapping("newfriend/add")
    public String add(
            Model model,
            @AuthenticationPrincipal MyUserDetails user,
            @RequestParam(name = "id", required = false) int id) {
        friendManageService.request(id, user.getId());
        notificationService.sendRequestNotice(user.getId(), id);
        return "user/friendmanage/main";
    }

    @GetMapping("newfriend/cancel")
    public String cancel(
            Model model,
            @AuthenticationPrincipal MyUserDetails user,
            @RequestParam(name = "id", required = false) int id) {
        friendManageService.cancel(id, user.getId());
        notificationService.deleteRequestNotice(user.getId(), id);
        return "user/friendmanage/main";
    }
}
