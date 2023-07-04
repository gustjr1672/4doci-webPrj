package com.doci.webPrj.user.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.GroupChallenge;
import com.doci.webPrj.user.entity.InvitationMember;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.repository.GroupChallengeRepository;
import com.doci.webPrj.user.repository.GroupStartRepository;
import com.doci.webPrj.user.repository.InvitationMemberViewRepository;
import com.doci.webPrj.user.repository.MemberRepository;

@Service
public class GroupChallengeServiceImp implements GroupChallengeService {
    @Autowired
    GroupChallengeRepository groupChallengeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    InvitationMemberViewRepository inviMemViewRepository;
    @Autowired
    GroupStartRepository groupStartRepository;

     public void addChallenge(GroupChallenge groupChallenge,int userId) {
         LocalDate startDate = groupChallenge.getStartDate();
         LocalDate endDate = groupChallenge.getEndDate();
         Period period = startDate.until(endDate);
         
         int days = period.getDays();
         
        groupChallenge.setGroupLeaderId(userId);
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

    @Override
    public List<InvitationMember> getInviList(int challengeId) {
        return inviMemViewRepository.findByChallengeId(challengeId);
    }


    @Override
    public List<Member> getNotInviList(int challengeId, List<Member> friendList) {
        return memberRepository.findListNotInvi(challengeId,friendList);
    }

    @Override
    public void groupStart(int challengeId, int userId){
        groupStartRepository.save(challengeId, userId);
    }
}
