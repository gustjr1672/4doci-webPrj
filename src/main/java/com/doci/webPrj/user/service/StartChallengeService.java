package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.admin.entity.RandomChallenge;
import com.doci.webPrj.user.entity.Choice;
import com.doci.webPrj.user.entity.FreeChallenge;

public interface StartChallengeService {

    void addFreeChallenge(FreeChallenge freeChallenge);

    List<RandomChallenge> getRandomList(String[] categoryIdList);

    void addRandomChallenge(Choice choice);

}
