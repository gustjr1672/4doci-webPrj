package com.doci.webPrj.scheduler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.doci.webPrj.scheduler.entity.UpdateView;
import com.doci.webPrj.scheduler.service.SchedulerService;
import com.doci.webPrj.user.entity.GroupChallenge;
import com.doci.webPrj.user.entity.PerformanceRecords;

@Service
public class Scheduler {

    @Autowired
    SchedulerService service;

    // isfinish 필요없을지도
    // 끝난도전은 isfinish바꿔주고 안끝났으면 회차계산해서 record 새로 추가해주는 함수
    // @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    // @Scheduled(cron = "*/10 * * * * *") // 매 10초마다 실행
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

    // 그룹도전 시작
    // @Scheduled(cron = "0 0 0/1 * * *") // 매 1시간마다 실행
    // @Scheduled(cron = "*/10 * * * * *") // 매 10초마다 실행
    void runScheduleGroupTask() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();
        System.out.println(hour);
        List<GroupChallenge> ChallengeList = service.getTodayGroupChallengeList(currentDate);
        for (GroupChallenge challenge : ChallengeList) {
            groupStart(challenge, hour);
        }
    }

    private void updateResult(PerformanceRecords record) {
        if (record.getResult().equals("진행중"))
            service.updateRecordResult(record, "실패");
    }

    // 초대,알림 삭제

    private void checkFinish(LocalDate currentDate, UpdateView challenge, String type) {
        if (challenge.isFinish() == false && (currentDate.isAfter(challenge.getStartDate()) ||
                currentDate.isEqual(challenge.getStartDate()))) {
            long days = challenge.getStartDate().until(currentDate, ChronoUnit.DAYS);
            updateAndInsert(currentDate, challenge, type, days);
        }
    }

    private void updateAndInsert(LocalDate currentDate, UpdateView challenge, String type, long days) {
        if (currentDate.isAfter(challenge.getEndDate())) {
            updateRecentRecord(type, challenge.getId());
            service.update(challenge, type);
            // result update기준 정해야함

        } else if (days % challenge.getAuthFrequency() == 0) {
            int round = (int) (days / challenge.getAuthFrequency()) + 1;

            if (round > challenge.getRecordRound() && !(round == 1 && type.equals("GS"))) {
                if (challenge.getRecordRound() != 0)
                    updateRecentRecord(type, challenge.getId());
                service.addRecord(round, challenge.getId(), type);

            }
        }
    }

    private void updateRecentRecord(String type, int updateId) {
        PerformanceRecords record = service.getRecentRecord(type, updateId);
        updateResult(record);
    }

    private void groupStart(GroupChallenge challenge, int hour) {
        List<UpdateView> list = null;
        if (challenge.getStartTime() == hour) {
            list = service.getGroupListByChallengeId(challenge.getId());
            for (UpdateView groupStart : list) {
                service.addRecord(1, groupStart.getId(), "GS");
            }
        }
    }
}
