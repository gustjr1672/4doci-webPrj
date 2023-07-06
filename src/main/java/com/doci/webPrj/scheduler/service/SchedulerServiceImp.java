package com.doci.webPrj.scheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.scheduler.entity.UpdateView;
import com.doci.webPrj.scheduler.repository.UpdateViewRepository;
import com.doci.webPrj.user.entity.PerformanceRecords;
import com.doci.webPrj.user.repository.ChoiceRepository;
import com.doci.webPrj.user.repository.FreeChallengeRepository;
import com.doci.webPrj.user.repository.GroupChallengeRepository;
import com.doci.webPrj.user.repository.GroupStartRepository;
import com.doci.webPrj.user.repository.PerformanceRecordsRepository;

@Service
public class SchedulerServiceImp implements SchedulerService {

    @Autowired
    UpdateViewRepository repository;
    @Autowired
    FreeChallengeRepository freeChallengeRepository;
    @Autowired
    ChoiceRepository choiceRepository;
    @Autowired
    GroupChallengeRepository groupChallengeRepository;
    @Autowired
    PerformanceRecordsRepository performanceRecordsRepository;
    @Autowired
    GroupStartRepository groupStartRepository;

    @Override
    public List<UpdateView> getFreeList() {

        return repository.findAllFree();
    }

    @Override
    public List<UpdateView> getRandomList() {

        return repository.findAllRandom();
    }

    @Override
    public List<UpdateView> getGroupList() {

        return repository.findAllGroup();
    }

    @Override
    public void update(UpdateView update, String type) {
        switch (type) {
            case "FC":
                freeChallengeRepository.update(update.getId());

                break;
            case "CH":
                choiceRepository.update(update.getId());
                break;
            case "GS":
                int groupChallengeId = groupStartRepository.getGroupChallengeId(update.getId());
                groupChallengeRepository.update(groupChallengeId);
                break;
        }
    }

    @Override
    public void addRecord(int round, int challengeId, String type) {
        PerformanceRecords Records = null;
        switch (type) {
            case "FC":
                Records = PerformanceRecords.builder()
                        .round(round)
                        .freeChallengeId(challengeId)
                        .build();
                break;
            case "CH":
                Records = PerformanceRecords.builder()
                        .round(round)
                        .choiceId(challengeId)
                        .build();
                break;
            case "GS":
                Records = PerformanceRecords.builder()
                        .round(round)
                        .groupStartId(challengeId)
                        .build();

                break;
        }

        performanceRecordsRepository.save(Records);

    }

    @Override
    public List<PerformanceRecords> getOngoingRecord() {

        return performanceRecordsRepository.findByResult();
    }

    @Override
    public PerformanceRecords getRecentRecord(String type, int id) {

        return performanceRecordsRepository.findCurrentRecord(type, id);
    }

    @Override
    public void updateRecordResult(PerformanceRecords record, String result) {

        performanceRecordsRepository.updateFail(record);
    }

}