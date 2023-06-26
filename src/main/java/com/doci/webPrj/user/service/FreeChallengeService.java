package com.doci.webPrj.user.service;

import com.doci.webPrj.user.entity.FreeChallenge;

public interface FreeChallengeService {
     void addFreeChallenge(FreeChallenge freeChallenge);

     int getFreechallengeId(String challengeName,int userId);

     void delete(int challengeId);
}
