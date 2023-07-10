package com.doci.webPrj.user.service;

import java.util.List;
import java.util.Map;

import com.doci.webPrj.user.entity.GroupChallenge;
import com.doci.webPrj.user.entity.InvitationMember;
import com.doci.webPrj.user.entity.Member;

public interface GroupChallengeService {
    void addChallenge(GroupChallenge groupChallenge,int userId);

    void delete(int challengeId);

    GroupChallenge getChallenge(int challengeId);

    Member getLeader(int userId);

    List<InvitationMember> getInviList(int challengeId);

    List<Member> getNotInviList(int challengeId, List<Member> friendList);

    void groupStart(int userId,int challengeId);

    void groupStartNow(int challengeId);

    void updateDate(Map<String, String> requestData);
}
