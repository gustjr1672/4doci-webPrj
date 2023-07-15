package com.doci.webPrj.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.CommentView;
import com.doci.webPrj.user.entity.Feed;
import com.doci.webPrj.user.entity.FeedDetail;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.service.CommentNotificationService;
import com.doci.webPrj.user.service.FeedService;
import com.doci.webPrj.user.service.FriendManageService;

@Controller
@RequestMapping("community")
public class CommunityController {

    @Autowired
    FriendManageService friendManageService;
    @Autowired
    FeedService feedService;
    @Autowired
    CommentNotificationService commentNotificationService;

    @GetMapping("feed")
    public String feed(
            Model model,
            @AuthenticationPrincipal MyUserDetails user,
            @RequestParam(name = "rid", required = false) Integer recordIdOfNewComment,
            @RequestParam(name = "nid", required = false) Integer notiIdOfNewComment) {
        List<Member> friendList = friendManageService.getFriendList(user.getId());
        List<Feed> feedList = feedService.getFeedList(friendList, user.getId());
        model.addAttribute("user", user);
        model.addAttribute("friendList", friendList);
        model.addAttribute("feedList", feedList);

        // 알림 모달에서 comment알림 클릭했을 때
        if (recordIdOfNewComment != null) {
            model.addAttribute("recordIdOfNewComment", recordIdOfNewComment);
            commentNotificationService.delete(notiIdOfNewComment);
        } else
            model.addAttribute("recordIdOfNewComment", "not-id");

        return "user/community/feed";
    }

    @GetMapping("feed/detail")
    public String detail(@RequestParam(name = "fc", defaultValue = "0") int fcId,
            @RequestParam(name = "ch", defaultValue = "0") int chId,
            @RequestParam(name = "gs", defaultValue = "0") int gsId,
            Model model) {

        List<FeedDetail> list = new ArrayList<>();
        List<CommentView> commentViewList = new ArrayList<>();

        int AllCommentCount = 0;
        if (fcId != 0) {
            list = feedService.getFreeFeedList(fcId);
            commentViewList = feedService.getCommentListByChallengeId(fcId);
        } else if (chId != 0) {
            list = feedService.getRandomFeedList(chId);
            commentViewList = feedService.getCommentListByChallengeId(chId);
        } else {
            list = feedService.getGroupFeedList(gsId);
            commentViewList = feedService.getCommentListByChallengeId(gsId);
        }
        for (FeedDetail feedDetail : list) {
            AllCommentCount += feedDetail.getCommentCount();
        }
        model.addAttribute("challenge", list.get(0));
        model.addAttribute("count", AllCommentCount);
        model.addAttribute("list", list);
        model.addAttribute("commentViewList", commentViewList);

        return "user/community/feed-detail";
    }

}
