package com.doci.webPrj.user.service;

import com.doci.webPrj.user.entity.AllChallenges;

import java.util.List;

public interface AllChallengesService {
    List<AllChallenges> findAll(int id);

    AllChallenges getChallenge(String challengeId);
}
