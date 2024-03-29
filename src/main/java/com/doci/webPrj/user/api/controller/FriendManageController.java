package com.doci.webPrj.user.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.service.FriendManageService;

@RequestMapping("friendmanage")
@RestController("apiFriendManageController")
public class FriendManageController {

    @Autowired
    private FriendManageService friendManageService;

    @GetMapping("newfriends/list")
    public List<Map<String, Object>> list(
            Model model,
            @AuthenticationPrincipal MyUserDetails user,
            @RequestParam(name = "n", required = false) String nickname) {

        List<Map<String, Object>> list = friendManageService.getNewMemberList(nickname, user.getNickname(), user);

        return list;
    }

    @GetMapping("friends/list/{nickname}")
    public List<Member> list(
            @AuthenticationPrincipal MyUserDetails user,
            @PathVariable("nickname") String nickname) {
        List<Member> friendList = friendManageService.getFriendListByNickname(user.getId(), nickname);
        return friendList;
    }

    @GetMapping("friends/list")
    public List<Member> list(
            @AuthenticationPrincipal MyUserDetails user) {
        List<Member> friendList = friendManageService.getFriendList(user.getId());
        return friendList;
    }

    @DeleteMapping("friends")
    public List<Member> delete(
            @AuthenticationPrincipal MyUserDetails user,
            @RequestParam(name = "id", required = false) int memberId,
            @RequestParam(name = "n", required = false) String nickname) {
        friendManageService.delete(memberId, user.getId());
        List<Member> list = new ArrayList<>();
        if (nickname.equals(""))
            list = friendManageService.getFriendList(user.getId());
        else
            list = friendManageService.getFriendListByNickname(user.getId(), nickname);

        return list;
    }

}
