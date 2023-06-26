package com.doci.webPrj.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.admin.entity.RandomChallenge;
import com.doci.webPrj.common.repository.RandomChallengeRepository;
import com.doci.webPrj.user.entity.Choice;
import com.doci.webPrj.user.repository.ChoiceRepository;

@Service
public class RandomChoiceServiceImp implements RandomChoiceService {
    @Autowired
    RandomChallengeRepository randomChallengeRepository;
    @Autowired
    ChoiceRepository choiceRepository;

   @Override
    public void addRandomChallenge(Choice choice) {
        choiceRepository.save(choice);
    }

    @Override
    public List<RandomChallenge> getRandomList(String[] categoryIdList) {
        return randomChallengeRepository.findRandomByCategory(categoryIdList);
    } 
}
