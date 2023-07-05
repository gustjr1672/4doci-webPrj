package com.doci.webPrj.scheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.scheduler.entity.FreeUpdate;
import com.doci.webPrj.scheduler.entity.GroupStartUpdate;
import com.doci.webPrj.scheduler.entity.RandomChoiceUpdate;
import com.doci.webPrj.scheduler.repository.UpdateViewRepository;
import com.doci.webPrj.user.repository.ChoiceRepository;
import com.doci.webPrj.user.repository.FreeChallengeRepository;
import com.doci.webPrj.user.repository.GroupChallengeRepository;
import com.doci.webPrj.user.repository.GroupStartRepository;

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

    @Override
    public List<FreeUpdate> getFreeList() {

        return repository.findAllFree();
    }

    @Override
    public List<RandomChoiceUpdate> getRandomList() {

        return repository.findAllRandom();
    }

    @Override
    public List<GroupStartUpdate> getGroupList() {

        return repository.findAllGroup();
    }

    @Override
    public void updateFree(FreeUpdate freeUpdate) {
        freeChallengeRepository.update(freeUpdate.getId());
    }

    @Override
    public void updateRandom(RandomChoiceUpdate randomChoiceUpdate) {
        choiceRepository.update(randomChoiceUpdate.getId());
    }

    @Override
    public void updateGroup(GroupStartUpdate groupStartUpdate) {
        groupChallengeRepository.update(groupStartUpdate.getId());
    }

}
