package com.doci.webPrj.user.api.controller;

import com.doci.webPrj.user.service.PerformanceRecordsService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("apiRecordController")
@RequestMapping("challenge")
public class PerformanceRecordsController {

    @Autowired
    PerformanceRecordsService recordsService;

    @PostMapping("achv-quantity")
    public void upAchvQuantity(@RequestParam("cid") String challengeTypeAndId) {
        recordsService.updateAchvQuantity(challengeTypeAndId);
    }

    @GetMapping("achv-quantity")
    public int getAchvQuantity(@RequestParam("cid") String challengeTypeAndId) {
        int achvQuantity = recordsService.getAchvQuantity(challengeTypeAndId);
        return achvQuantity;
    }

    @PutMapping("performance-records")
    public void edit(@RequestBody Map<String, String> requestData) {

        String impression = requestData.get("impression");
        int achvQuantity = Integer.parseInt(requestData.get("achvQuantity"));
        int id = Integer.parseInt(requestData.get("id"));

        recordsService.edit(impression, achvQuantity, id);

    }
}
