package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.admin.entity.RandomChallenge;
import com.doci.webPrj.user.entity.Choice;
import com.doci.webPrj.user.entity.FreeChallenge;
import com.doci.webPrj.user.entity.Invitation;

public interface StartChallengeService {

    void addFreeChallenge(FreeChallenge freeChallenge);

    
    List<RandomChallenge> getRandomList(String[] categoryIdList);

    void addRandomChallenge(Choice choice);

    int getFreechallengeId(String challengeName,int userId);

    void delete(int challengeId);


}
