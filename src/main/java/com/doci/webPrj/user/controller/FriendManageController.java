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
import com.doci.webPrj.user.entity.OngoingChallengeView;
import com.doci.webPrj.user.entity.PastChallengeView;
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
        List<Member> friendList = friendManageService.getFriendList(user.getId());
        model.addAttribute("friendList", friendList);
        model.addAttribute("total", friendList.size());
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

    @GetMapping("challenge")
    public String challengeOfFriend(
            @RequestParam(name = "id", required = false) int id,
            @AuthenticationPrincipal MyUserDetails user,
            Model model) {
        Member friend = friendManageService.getFriendById(id);
        List<OngoingChallengeView> ongoingList = friendManageService.getOngoingList(id, user.getId());
        List<PastChallengeView> pastList = friendManageService.getPastList(id, user.getId());
        model.addAttribute("friend", friend);
        model.addAttribute("pastList", pastList);
        model.addAttribute("ongoingList", ongoingList);
        return "user/friendmanage/challenge-of-friend";
    }
}
