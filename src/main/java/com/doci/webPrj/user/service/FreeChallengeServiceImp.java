package com.doci.webPrj.user.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

import com.doci.webPrj.user.entity.PerformanceRecords;
import com.doci.webPrj.user.repository.PerformanceRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.ChallengeFormView;
import com.doci.webPrj.user.entity.FreeChallenge;
import com.doci.webPrj.user.repository.ChallengeViewRepository;
import com.doci.webPrj.user.repository.FreeChallengeRepository;

@Service
public class FreeChallengeServiceImp implements FreeChallengeService {

    @Autowired
    FreeChallengeRepository freeChallengeRepository;
    @Autowired
    PerformanceRecordsRepository recordsRepository;
    @Autowired
    ChallengeViewRepository challengeViewRepository;

    @Override
    public void addChallenge(FreeChallenge freeChallenge) {
        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = freeChallenge.getStartDate();
        LocalDate endDate = freeChallenge.getEndDate();
        Period period = startDate.until(endDate);

        int days = period.getDays();

        freeChallenge.setPeriod(days);
        freeChallengeRepository.save(freeChallenge);
        int freeChallengeId = freeChallenge.getId();

        if (currentDate.isEqual(startDate)) {
            savePerformanceRecords(freeChallengeId);
        }
    }

    private void savePerformanceRecords(int freeChallengeId) {
        PerformanceRecords record = PerformanceRecords.builder()
                .round(1)
                .freeChallengeId(freeChallengeId)
                .build();
        recordsRepository.save(record);
    }

    @Override
    public void nowStart(int freeChallengeId) {
        LocalDate currentDate = LocalDate.now();
        freeChallengeRepository.updateStartDate(freeChallengeId,currentDate);
        savePerformanceRecords(freeChallengeId);
    }

    @Override
    public ChallengeFormView getFriendForm(String uniqueId) {
        return challengeViewRepository.getChallengeFormById(uniqueId);
    }

    @Override
    public void updateDate(Map<String, String> requestData) {
        String startDate = requestData.get("startDate");
        String endDate = requestData.get("endDate");
        int challengeId = Integer.parseInt(requestData.get("challengeId"));
        freeChallengeRepository.updateDate(challengeId, startDate, endDate);
    }

    @Override
    public FreeChallenge getChallenge(int challengeId) {
        return freeChallengeRepository.findById(challengeId);
    }

}
