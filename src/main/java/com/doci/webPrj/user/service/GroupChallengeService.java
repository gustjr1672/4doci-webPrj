package com.doci.webPrj.user.service;

import com.doci.webPrj.user.entity.GroupChallenge;

public interface GroupChallengeService {
    void addChallenge(GroupChallenge groupChallenge);

    void delete(int challengeId);
}
