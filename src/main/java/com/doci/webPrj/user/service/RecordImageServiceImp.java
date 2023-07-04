package com.doci.webPrj.user.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.doci.webPrj.user.entity.PerformanceRecords;
import com.doci.webPrj.user.repository.PerformanceRecordsRepository;

@Service
public class RecordImageServiceImp implements RecordImageService {

    @Autowired
    PerformanceRecordsRepository performanceRecordsRepository;

    @Value("${record.dir}")
    private String recordDir;

    @Override
    public PerformanceRecords save(MultipartFile file, PerformanceRecords performanceRecords) {
        String filename = file.getOriginalFilename();
        String fullPath = recordDir + createStoreFileName(filename);

        localDirectorySave(file, fullPath);

        String relativePath = fullPath.substring(fullPath.indexOf("/upload"));
        performanceRecords.setImage(relativePath);

        return performanceRecords;
    }

    private void localDirectorySave(MultipartFile file, String fullPath) {
        try {
            Path storePath = Path.of(fullPath);
            byte[] fileBytes = file.getBytes(); // 파일 데이터 byte로 읽어옴

            Files.write(storePath, fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 파일 이름 난수화
    private String createStoreFileName(String originFilename) {
        String ext = extractExt(originFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    // 파일의 확장자만 추출
    private String extractExt(String originFilename) {
        int pos = originFilename.lastIndexOf(".");
        return originFilename.substring(pos + 1);
    }
}
