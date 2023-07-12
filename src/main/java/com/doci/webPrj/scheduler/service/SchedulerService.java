package com.doci.webPrj.scheduler.service;

import java.time.LocalDate;
import java.util.List;

import com.doci.webPrj.scheduler.entity.UpdateView;
import com.doci.webPrj.user.entity.GroupChallenge;
import com.doci.webPrj.user.entity.PerformanceRecords;

public interface SchedulerService {

    List<UpdateView> getFreeList();

    List<UpdateView> getRandomList();

    List<UpdateView> getGroupList();

    void update(UpdateView update, String type);

    void addRecord(int round, int freeChallengeId, String type);

    List<PerformanceRecords> getOngoingRecord();

    PerformanceRecords getRecentRecord(String type, int id);

    void updateRecordResult(PerformanceRecords record, String result);

    List<GroupChallenge> getTodayGroupChallengeList(LocalDate currentDate);

    List<UpdateView> getGroupListByChallengeId(int id);

    void updateGroupResult(UpdateView challenge);

}
