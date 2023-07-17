package com.doci.webPrj.user.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    public void nowStart(int choiceId) {
        LocalDate currentDate = LocalDate.now();
        choiceRepository.updateStartDate(choiceId,currentDate);
        savePerformanceRecords(choiceId);
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

    @Override
    public void updateDate(Map<String, String> requestData) {
        String startDate = requestData.get("startDate");
        String endDate = requestData.get("endDate");
        int choiceId = Integer.parseInt(requestData.get("challengeId"));
        choiceRepository.updateDate(choiceId, startDate, endDate);
    }

    @Override
    public Choice getChoice(int choiceId) {
        return choiceRepository.findById(choiceId);
    }
}
