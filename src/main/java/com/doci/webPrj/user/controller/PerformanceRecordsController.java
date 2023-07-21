package com.doci.webPrj.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.AllChallenges;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.entity.PerformanceRecords;
import com.doci.webPrj.user.repository.MemberRepository;
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

    @GetMapping("performance-records/group")
    public String groupPerformanceRecords(@AuthenticationPrincipal MyUserDetails user, Model model,
            @RequestParam(name = "cid") String challengeId) {
        PerformanceRecords performanceRecords = performanceRecordsService.getCurrentRecord(challengeId);

        // 도전정보
        AllChallenges allChallenges = allChallengesService.getChallenge(challengeId);
        List<Member> memberList = performanceRecordsService.getMemberListByChalId(challengeId, user.getId());
        int groupChallengeId = performanceRecordsService.getGroupChallengeid(challengeId);
        model.addAttribute("user", user);
        model.addAttribute("groupChallengeId", groupChallengeId);
        model.addAttribute("perfomanceRecord", performanceRecords);
        model.addAttribute("memberList", memberList);
        model.addAttribute("allChallenges", allChallenges);
        return "user/group-performance-record";
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
