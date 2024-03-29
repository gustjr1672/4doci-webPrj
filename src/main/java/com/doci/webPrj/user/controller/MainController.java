package com.doci.webPrj.user.controller;

import java.time.LocalDate;
import java.util.List;

import com.doci.webPrj.admin.entity.Unit;
import com.doci.webPrj.admin.service.UnitService;
import com.doci.webPrj.user.entity.AllChallenges;
import com.doci.webPrj.user.service.AllChallengesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.service.NotificationService;

@Controller("userMainController")
public class MainController {

    @Autowired
    NotificationService notificationService;
    @Autowired
    AllChallengesService allChallengesService;


    @GetMapping("main")
    public String main(Model model, @AuthenticationPrincipal MyUserDetails user) {
        boolean isNoti = notificationService.getNotiStatus(user.getId());
        
        List<AllChallenges> allChallengesList = allChallengesService.findAll(user.getId());

        long count = allChallengesList.stream()
                .filter(allChallenges -> allChallenges.getPerformanceRecordsId() != null)
                .count();

        model.addAttribute("ongoingChallengeCnt", count);
        LocalDate currentDate = LocalDate.now();

        model.addAttribute("allChallengesList", allChallengesList);
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("user", user);
        model.addAttribute("isNoti", isNoti);
        return "user/main";
    }

}
