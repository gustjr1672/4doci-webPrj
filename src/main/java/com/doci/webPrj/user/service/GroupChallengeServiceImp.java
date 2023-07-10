package com.doci.webPrj.user.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
    public void groupStart(int userId,int challengeId){
        groupStartRepository.save(userId, challengeId);
    }

    @Override
    public void groupStartNow(int challengeId){
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime currentDateTime = LocalDateTime.now();
        int currentHour = currentDateTime.getHour();

        System.out.println(currentDateTime);
        System.out.println(currentHour);
        groupChallengeRepository.updateDate(challengeId,formattedDate,currentHour,null);
    }

    @Override
    public void updateDate(Map<String, String> requestData) {
        String startDate = requestData.get("startDate");
        int startTime =  Integer.parseInt(requestData.get("startTime"));
        String endDate = requestData.get("endDate");
        int challengeId = Integer.parseInt(requestData.get("challengeId"));
        groupChallengeRepository.updateDate(challengeId,startDate,startTime,endDate);
    }

    @Override
    public int getGroupChallengeIdByGsId(int groupStartId) {
        return groupChallengeRepository.getGroupChallengeIdByGsId(groupStartId);
    }
}
