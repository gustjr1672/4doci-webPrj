package com.doci.webPrj.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.Comment;
import com.doci.webPrj.user.entity.Feed;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.service.FeedService;
import com.doci.webPrj.user.service.FriendManageService;

@Controller
@RequestMapping("community")
public class CommunityController {

    @Autowired
    FriendManageService friendManageService;
    @Autowired
    FeedService feedService;

    @GetMapping("feed")
    public String feed(
            Model model,
            @AuthenticationPrincipal MyUserDetails user) {
        List<Member> friendList = friendManageService.getFriendList(user.getId());
        // Map<Feed, List<Comment>> feedList = feedService.getFeedList(friendList,
        // user.getId());
        model.addAttribute("user", user);
        model.addAttribute("friendList", friendList);
        // model.addAttribute("feedList", feedList);

        return "user/community/feed";
    }
}
