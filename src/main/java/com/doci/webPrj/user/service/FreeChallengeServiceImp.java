package com.doci.webPrj.user.service;

import java.time.LocalDate;
import java.time.Period;

import com.doci.webPrj.user.entity.PerformanceRecords;
import com.doci.webPrj.user.repository.PerformanceRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.FreeChallenge;
import com.doci.webPrj.user.repository.FreeChallengeRepository;

@Service
public class FreeChallengeServiceImp implements FreeChallengeService {
    
    @Autowired
    FreeChallengeRepository freeChallengeRepository;
    @Autowired
    PerformanceRecordsRepository recordsRepository;
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
    

}
