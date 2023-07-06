package com.doci.webPrj.scheduler;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.doci.webPrj.scheduler.entity.UpdateView;
import com.doci.webPrj.scheduler.service.SchedulerService;
import com.doci.webPrj.user.entity.PerformanceRecords;

@Service
public class Scheduler {

    @Autowired
    SchedulerService service;

    // isfinish 필요없을지도
    // 끝난도전은 isfinish바꿔주고 안끝났으면 회차계산해서 record 새로 추가해주는 함수
    // @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
//    @Scheduled(cron = "*/10 * * * * *") // 매 10초마다 실행
    void runScheduleTask() {
        LocalDate currentDate = LocalDate.now();
        List<UpdateView> freeList = service.getFreeList();

        for (UpdateView freeUpdate : freeList) {
            checkFinish(currentDate, freeUpdate, "FC");

        }
        List<UpdateView> randomList = service.getRandomList();

        for (UpdateView randomChoiceUpdate : randomList) {
            checkFinish(currentDate, randomChoiceUpdate, "CH");

        }
        List<UpdateView> groupList = service.getGroupList();

        for (UpdateView groupStartUpdate : groupList) {
            checkFinish(currentDate, groupStartUpdate, "GS");
        }
    }

    private void updateResult(PerformanceRecords record) {
        if (record.getResult().equals("진행중"))
            service.updateRecordResult(record, "실패");
    }

    private void checkFinish(LocalDate currentDate, UpdateView update, String type) {
        long days = update.getStartDate().until(currentDate, ChronoUnit.DAYS);
        if (update.isFinish() == false && (currentDate.isAfter(update.getStartDate()) ||
                currentDate.isEqual(update.getStartDate()))) {
            updateAndInsert(currentDate, update, type, days);
        }
    }

    private void updateAndInsert(LocalDate currentDate, UpdateView update, String type, long days) {
        if (currentDate.isAfter(update.getEndDate())) {
            updateRecentRecord(type, update.getId());
            service.update(update, type);
            // result update기준 정해야함
        } else if (days % update.getAuthFrequency() == 0) {
            int round = (int) (days / update.getAuthFrequency()) + 1;
            if (round > update.getRecordRound()) {
                updateRecentRecord(type, update.getId());
                service.addRecord(round, update.getId(), type);
            }
        }
    }

    private void updateRecentRecord(String type, int updateId) {
        PerformanceRecords record = service.getRecentRecord(type, updateId);
        updateResult(record);
    }
}
