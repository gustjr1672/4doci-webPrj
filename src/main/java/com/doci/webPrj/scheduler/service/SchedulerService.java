package com.doci.webPrj.scheduler.service;

import java.util.List;

import com.doci.webPrj.scheduler.entity.FreeUpdate;
import com.doci.webPrj.scheduler.entity.GroupStartUpdate;
import com.doci.webPrj.scheduler.entity.RandomChoiceUpdate;

public interface SchedulerService {

    List<FreeUpdate> getFreeList();

    List<RandomChoiceUpdate> getRandomList();

    List<GroupStartUpdate> getGroupList();

    void updateFree(FreeUpdate freeUpdate);

    void updateRandom(RandomChoiceUpdate randomChoiceUpdate);

    void updateGroup(GroupStartUpdate groupStartUpdate);

}
