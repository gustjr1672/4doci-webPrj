package com.doci.webPrj.user.controller;

import java.util.List;

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

        // 전체 수행기록 리스트
        List<PerformanceRecords> performanceRecordList = performanceRecordsService.getList(challengeId);
        model.addAttribute("performanceRecordList", performanceRecordList);

        // 현재 수행기록(리스트에서 마지막)
        int size = performanceRecordList.size();
        model.addAttribute("perfomanceRecord", performanceRecordList.get(size - 1));

        // 도전정보
        AllChallenges allChallenges = allChallengesService.getChallenge(challengeId);
        model.addAttribute("allChallenges", allChallenges);

        return "user/performance-records";
    }

    @GetMapping("performance-records/delete")
    public String PerformanceRecordsDelete(@RequestParam(name = "cid") String challengeId) {

        performanceRecordsService.deleteChallenge(challengeId);

        return "redirect:/main";
    }

}
