package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.admin.entity.ChoiceRandomList;
import com.doci.webPrj.user.entity.Choice;

public interface RandomChoiceService {
    List<ChoiceRandomList> getRandomList(String[] categoryIdList);

    void addRandomChallenge(Choice choice);

    void nowStart(int freeChallengeId);
}
