package com.doci.webPrj.user.service;

import com.doci.webPrj.user.entity.AllChallenges;
import com.doci.webPrj.user.repository.AllChallengesRepository;
import com.doci.webPrj.user.repository.ChoiceRepository;
import com.doci.webPrj.user.repository.FreeChallengeRepository;
import com.doci.webPrj.user.repository.GroupStartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllChallengesServiceImp implements AllChallengesService {

    private final String FREECHALLENGE = "FC";
    private final String CHOICE = "CH";
    private final String GROUPSTART = "GS";
    private final String UNDERBAR = "_";

    @Autowired
    AllChallengesRepository allChallengesRepository;
    @Autowired
    FreeChallengeRepository freeChallengeRepository;
    @Autowired
    ChoiceRepository choiceRepository;
    @Autowired
    GroupStartRepository groupStartRepository;

    @Override
    public List<AllChallenges> findAll(int id) {

        return allChallengesRepository.findAll(id);
    }

    @Override
    public AllChallenges getChallenge(String challengeId) {

        AllChallenges allChallenges = allChallengesRepository.findChallenge(challengeId);

        return allChallenges;
    }

    @Override
    public void editVisibility(String uniqueId, boolean visibility) {
        int index = uniqueId.indexOf(UNDERBAR);
        String challengeType = uniqueId.substring(0, index);
        int id = Integer.parseInt(uniqueId.substring(index + 1));
        switch (challengeType) {
            case FREECHALLENGE:
                freeChallengeRepository.editVisibility(id, visibility);
                break;
            case CHOICE:
                choiceRepository.editVisibility(id, visibility);
                break;
        }
    }
}
