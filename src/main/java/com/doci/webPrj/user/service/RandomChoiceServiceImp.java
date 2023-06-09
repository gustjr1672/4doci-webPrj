package com.doci.webPrj.user.service;

import java.time.LocalDate;
import java.util.List;

import com.doci.webPrj.user.entity.PerformanceRecords;
import com.doci.webPrj.user.repository.PerformanceRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.admin.entity.ChoiceRandomList;
import com.doci.webPrj.admin.entity.RandomChallenge;
import com.doci.webPrj.common.repository.RandomChallengeRepository;
import com.doci.webPrj.user.entity.Choice;
import com.doci.webPrj.user.repository.ChoiceRepository;

@Service
public class RandomChoiceServiceImp implements RandomChoiceService {
    @Autowired
    RandomChallengeRepository randomChallengeRepository;
    @Autowired
    ChoiceRepository choiceRepository;
    @Autowired
    PerformanceRecordsRepository recordsRepository;

    @Override
    public void addRandomChallenge(Choice choice) {
        choiceRepository.save(choice);
        int choiceId = choice.getId();

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = choice.getStartDate();

        if (currentDate.isEqual(startDate)) {
            savePerformanceRecords(choiceId);
        }
    }

    @Override
    public List<ChoiceRandomList> getRandomList(String[] categoryIdList) {
        return randomChallengeRepository.findRandomByCategory(categoryIdList);
    }

    private void savePerformanceRecords(int choiceId) {
        PerformanceRecords record = PerformanceRecords.builder()
                .round(1)
                .choiceId(choiceId)
                .build();
        recordsRepository.save(record);
    }
}
