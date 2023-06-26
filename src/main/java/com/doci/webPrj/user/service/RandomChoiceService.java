package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.admin.entity.RandomChallenge;
import com.doci.webPrj.user.entity.Choice;

public interface RandomChoiceService {
    List<RandomChallenge> getRandomList(String[] categoryIdList);

    void addRandomChallenge(Choice choice);
}
