package com.doci.webPrj.user.service;

import com.doci.webPrj.user.entity.ChallengeFormView;
import com.doci.webPrj.user.entity.FreeChallenge;

public interface FreeChallengeService {
     void addChallenge(FreeChallenge freeChallenge);

     ChallengeFormView getFriendForm(String uniqueId);

     void nowStart(int freeChallengeId);

}
