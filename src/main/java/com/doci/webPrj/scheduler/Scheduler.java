package com.doci.webPrj.scheduler;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.doci.webPrj.scheduler.entity.FreeUpdate;
import com.doci.webPrj.scheduler.entity.GroupStartUpdate;
import com.doci.webPrj.scheduler.entity.RandomChoiceUpdate;
import com.doci.webPrj.scheduler.service.SchedulerService;

@Service
public class Scheduler {

    @Autowired
    SchedulerService service;

    // isfinish 필요없을지도
    // @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    @Scheduled(cron = "*/10 * * * * *") // 매 10초마다 실행
    void addRecord() {
        LocalDate currentDate = LocalDate.now();
        List<FreeUpdate> freeList = service.getFreeList();

        for (FreeUpdate freeUpdate : freeList) {
            long days = freeUpdate.getStartDate().until(currentDate, ChronoUnit.DAYS);
            if (freeUpdate.isFinish() == false && (currentDate.isAfter(freeUpdate.getStartDate()) ||
                    currentDate.isEqual(freeUpdate.getStartDate()))) {
                if (currentDate.isAfter(freeUpdate.getEndDate())) {
                    service.updateFree(freeUpdate);
                    // result update기준 정해야함
                } else if (days % freeUpdate.getAuthFrequency() == 0) {
                    int round = (int) (days / freeUpdate.getAuthFrequency()) + 1;
                    if (round > freeUpdate.getRecordRound()) {
                        service.addFreeRecord(round, freeUpdate.getId());
                    }
                }
            }

        }
        List<RandomChoiceUpdate> randomList = service.getRandomList();

        for (RandomChoiceUpdate randomChoiceUpdate : randomList) {
            long days = randomChoiceUpdate.getStartDate().until(currentDate, ChronoUnit.DAYS);
            if (randomChoiceUpdate.isFinish() == false && (currentDate.isAfter(randomChoiceUpdate.getStartDate()) ||
                    currentDate.isEqual(randomChoiceUpdate.getStartDate()))) {
                if (currentDate.isAfter(randomChoiceUpdate.getEndDate())) {
                    service.updateRandom(randomChoiceUpdate);
                } else if (days % randomChoiceUpdate.getAuthFrequency() == 0) {
                    int round = (int) (days / randomChoiceUpdate.getAuthFrequency()) + 1;
                    if (round > randomChoiceUpdate.getRecordRound()) {
                        service.addRandomRecord(round, randomChoiceUpdate.getId());
                    }
                }
            }

        }
        List<GroupStartUpdate> groupList = service.getGroupList();

        for (GroupStartUpdate groupStartUpdate : groupList) {
            long days = groupStartUpdate.getStartDate().until(currentDate, ChronoUnit.DAYS);
            if (groupStartUpdate.isFinish() == false && (currentDate.isAfter(groupStartUpdate.getStartDate()) ||
                    currentDate.isEqual(groupStartUpdate.getStartDate()))) {
                if (currentDate.isAfter(groupStartUpdate.getEndDate())) {
                    service.updateGroup(groupStartUpdate);
                } else if (days % groupStartUpdate.getAuthFrequency() == 0) {
                    int round = (int) (days / groupStartUpdate.getAuthFrequency()) + 1;
                    if (round > groupStartUpdate.getRecordRound()) {
                        service.addGroupRecord(round, groupStartUpdate.getId());
                    }
                }
            }
        }
    }

    // @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    @Scheduled(cron = "*/10 * * * * *") // 매 10초마다 실행
    void updateResult() {

    }
}
