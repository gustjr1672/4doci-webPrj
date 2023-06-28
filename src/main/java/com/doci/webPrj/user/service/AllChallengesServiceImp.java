package com.doci.webPrj.user.service;

import com.doci.webPrj.user.entity.AllChallenges;
import com.doci.webPrj.user.repository.AllChallengesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllChallengesServiceImp implements AllChallengesService {

    @Autowired
    AllChallengesRepository allChallengesRepository;

    @Override
    public List<AllChallenges> findAll(int id) {

        return allChallengesRepository.findAll(id);
    }

    @Override
    public AllChallenges getChallenge(String challengeId) {

        AllChallenges allChallenges = allChallengesRepository.findChallenge(challengeId);

        return allChallenges;
    }
}
