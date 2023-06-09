package com.doci.webPrj.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.doci.webPrj.user.entity.AllChallenges;
import com.doci.webPrj.user.entity.PerformanceRecords;
import com.doci.webPrj.user.service.AllChallengesService;
import com.doci.webPrj.user.service.PerformanceRecordsService;

@Controller
public class PerformanceRecordsController {

    @Autowired
    PerformanceRecordsService performanceRecordsService;

    @Autowired
    AllChallengesService allChallengesService;

    @GetMapping("performance-records")
    public String PerformanceRecords(Model model,
            @RequestParam(name = "cid") String challengeId) {

        boolean visibility = performanceRecordsService.getVisibility(challengeId);
        // 현재 수행기록
        PerformanceRecords performanceRecords = performanceRecordsService.getCurrentRecord(challengeId);
        model.addAttribute("perfomanceRecord", performanceRecords);

        // 도전정보
        AllChallenges allChallenges = allChallengesService.getChallenge(challengeId);
        model.addAttribute("visibility", visibility);
        model.addAttribute("allChallenges", allChallenges);

        return "user/performance-records";
    }

    @GetMapping("performance-records/delete")
    public String PerformanceRecordsDelete(@RequestParam(name = "cid") String challengeId) {

        performanceRecordsService.deleteChallenge(challengeId);

        return "redirect:/main";
    }

    @GetMapping("performance-waiting")
    public String performanceWaiting(Model model, @RequestParam(name = "cid") String challengeId) {
        AllChallenges challenge = allChallengesService.getChallenge(challengeId);
        model.addAttribute("challenge", challenge);

        return "user/performance-waiting";
    }

}
