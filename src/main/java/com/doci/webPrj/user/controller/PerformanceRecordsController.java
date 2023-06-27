package com.doci.webPrj.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PerformanceRecordsController {

    @GetMapping("performance-record")
    public String PerformanceRecords(@RequestParam(name = "cid") String challengeId) {

        return null;
    }
}
