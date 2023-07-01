package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.user.entity.PerformanceRecords;

public interface PerformanceRecordsService {
    void updateAchvQuantity(String challengeId);

    int getAchvQuantity(String challengeId);

    List<PerformanceRecords> getList(String challengeId);

    void editRecords(String impression, int achvQuantity, int id);

    void deleteChallenge(String challengeId);

    void updateResultOfRound(int achvQuantity, int id, String uniqueId);

    PerformanceRecords getCurrentRecord(String challengeId);

}
