package com.doci.webPrj.user.api.controller;

import com.doci.webPrj.user.entity.PerformanceRecords;
import com.doci.webPrj.user.service.PerformanceRecordsService;
import com.doci.webPrj.user.service.RecordImageService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("apiRecordController")
@RequestMapping("challenge")
public class PerformanceRecordsController {

    @Autowired
    PerformanceRecordsService recordsService;
    @Autowired
    RecordImageService recordImageService;

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
    public void edit(PerformanceRecords performanceRecords,
            @RequestParam(name = "uniqueId", required = false) String uniqueId,
            @RequestParam(name = "file", required = false) MultipartFile file) throws IOException {

        PerformanceRecords record = performanceRecords;

        if (file != null) {
            record = recordImageService.save(file, record);
        }

        recordsService.editRecords(record);

        recordsService.updateResultOfRound(record, uniqueId);

    }

    @GetMapping("performance-records")
    public List<PerformanceRecords> PerformanceRecords(@RequestParam(name = "cid") String challengeTypeAndId) {

        // 전체 수행기록 리스트
        List<PerformanceRecords> performanceRecordList = recordsService.getList(challengeTypeAndId);

        return performanceRecordList;
    }
}
