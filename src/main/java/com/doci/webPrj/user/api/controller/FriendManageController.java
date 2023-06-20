package com.doci.webPrj.user.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.service.FriendManageService;

@RequestMapping("friendmanage")
@RestController("apiFriendManageController")
public class FriendManageController {

    @Autowired
    private FriendManageService friendManageService;

    @GetMapping("newfriend/search")
    public List<Map<String, Object>> search(
            Model model,
            @AuthenticationPrincipal MyUserDetails user,
            @RequestParam(name = "n", required = false) String nickname) {

        List<Map<String, Object>> response = friendManageService.getNewMemberList(nickname, user.getNickname(), user);

        return response;
    }
}
