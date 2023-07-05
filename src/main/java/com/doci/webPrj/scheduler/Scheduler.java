package com.doci.webPrj.scheduler;

import java.time.LocalDate;
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

    // @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "*/10 * * * * *") // 매 10초마다 실행
    void runScheduledTask() {
        LocalDate currentDate = LocalDate.now();
        List<FreeUpdate> freeList = service.getFreeList();

        for (FreeUpdate freeUpdate : freeList) {
            if (freeUpdate.isFinish() == false && (currentDate.isAfter(freeUpdate.getStartDate()) ||
                    currentDate.isEqual(freeUpdate.getStartDate()))) {
                if (currentDate.isAfter(freeUpdate.getEndDate())) {
                    service.updateFree(freeUpdate);
                }
            }

        }
        List<RandomChoiceUpdate> randomList = service.getRandomList();

        for (RandomChoiceUpdate randomChoiceUpdate : randomList) {
            if (randomChoiceUpdate.isFinish() == false && (currentDate.isAfter(randomChoiceUpdate.getStartDate()) ||
                    currentDate.isEqual(randomChoiceUpdate.getStartDate()))) {
                if (currentDate.isAfter(randomChoiceUpdate.getEndDate())) {
                    service.updateRandom(randomChoiceUpdate);
                }
            }

        }
        List<GroupStartUpdate> groupList = service.getGroupList();

        for (GroupStartUpdate groupStartUpdate : groupList) {
            if (groupStartUpdate.isFinish() == false && (currentDate.isAfter(groupStartUpdate.getStartDate()) ||
                    currentDate.isEqual(groupStartUpdate.getStartDate()))) {
                if (currentDate.isAfter(groupStartUpdate.getEndDate())) {
                    service.updateGroup(groupStartUpdate);
                }
            }
        }
    }

}
