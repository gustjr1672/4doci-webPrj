package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.user.entity.PerformanceRecords;

public interface PerformanceRecordsService {
    void updateAchvQuantity(String challengeId);

    int getAchvQuantity(String challengeId);

    List<PerformanceRecords> getList(String challengeId);

    void editRecords(PerformanceRecords performanceRecords);

    void deleteChallenge(String challengeId);

    void updateResultOfRound(PerformanceRecords performanceRecords, String uniqueId);

    PerformanceRecords getCurrentRecord(String challengeId);

}
