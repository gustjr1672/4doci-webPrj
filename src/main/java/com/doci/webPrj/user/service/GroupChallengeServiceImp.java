package com.doci.webPrj.user.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.GroupChallenge;
import com.doci.webPrj.user.repository.GroupChallengeRepository;

@Service
public class GroupChallengeServiceImp implements GroupChallengeService {
    @Autowired
    GroupChallengeRepository groupChallengeRepository;

     public void addChallenge(GroupChallenge groupChallenge) {
        LocalDate startDate = groupChallenge.getStartDate();
        LocalDate endDate = groupChallenge.getEndDate();
        Period period = startDate.until(endDate);

        int days = period.getDays();

        groupChallenge.setPeriod(days);
        groupChallengeRepository.save(groupChallenge);

    }

    @Override
    public void delete(int challengeId) {
        groupChallengeRepository.delete(challengeId);
    }

    @Override
    public GroupChallenge getChallenge(int challengeId) {
        return groupChallengeRepository.findById(challengeId);
    }
}
