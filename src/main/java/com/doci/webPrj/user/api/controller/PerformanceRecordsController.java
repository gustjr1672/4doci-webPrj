package com.doci.webPrj.user.api.controller;

import com.doci.webPrj.user.service.PerformanceRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("apiRecordController")
@RequestMapping("challenge")
public class PerformanceRecordsController {

    @Autowired
    PerformanceRecordsService recordsService;

    @PostMapping("achv-quantity")
    public void upAchvQuantity(@RequestParam("cid") String challengeTypeAndId){
        recordsService.updateAchvQuantity(challengeTypeAndId);
    }

    @GetMapping("achv-quantity")
    public int getAchvQuantity(@RequestParam("cid") String challengeTypeAndId){
        int achvQuantity = recordsService.getAchvQuantity(challengeTypeAndId);
        return achvQuantity;
    }
}
