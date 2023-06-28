package com.doci.webPrj.user.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.GroupChallenge;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.repository.GroupChallengeRepository;
import com.doci.webPrj.user.repository.MemberRepository;

@Service
public class GroupChallengeServiceImp implements GroupChallengeService {
    @Autowired
    GroupChallengeRepository groupChallengeRepository;
    @Autowired
    MemberRepository memberRepository;
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

    @Override
    public Member getLeader(int userId) {
       return memberRepository.findById(userId);
    }
}
