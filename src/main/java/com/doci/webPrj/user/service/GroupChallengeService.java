package com.doci.webPrj.user.service;

import com.doci.webPrj.user.entity.GroupChallenge;
import com.doci.webPrj.user.entity.Member;

public interface GroupChallengeService {
    void addChallenge(GroupChallenge groupChallenge);

    void delete(int challengeId);

    GroupChallenge getChallenge(int challengeId);

    Member getLeader(int userId);
}
