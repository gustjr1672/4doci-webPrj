package com.doci.webPrj.user.service;

import org.springframework.web.multipart.MultipartFile;

import com.doci.webPrj.user.entity.PerformanceRecords;

public interface RecordImageService {
    public PerformanceRecords save(MultipartFile file, PerformanceRecords performanceRecords);

}
