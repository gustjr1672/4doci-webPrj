package com.doci.webPrj.user.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.admin.entity.RandomChallenge;
import com.doci.webPrj.common.repository.RandomChallengeRepository;
import com.doci.webPrj.user.entity.Choice;
import com.doci.webPrj.user.entity.FreeChallenge;
import com.doci.webPrj.user.repository.ChoiceRepository;
import com.doci.webPrj.user.repository.FreeChallengeRepository;

@Service
public class StartChallengeServiceImp implements StartChallengeService {

    @Autowired
    FreeChallengeRepository freeChallengeRepository;
    @Autowired
    RandomChallengeRepository randomChallengeRepository;
    @Autowired
    ChoiceRepository choiceRepository;

    public void addFreeChallenge(FreeChallenge freeChallenge) {
        LocalDate startDate = freeChallenge.getStartDate();
        LocalDate endDate = freeChallenge.getEndDate();
        Period period = startDate.until(endDate);

        int days = period.getDays();

        freeChallenge.setPeriod(days);
        freeChallengeRepository.save(freeChallenge);
    }

    @Override
    public void addRandomChallenge(Choice choice) {
        choiceRepository.save(choice);
    }

    @Override
    public List<RandomChallenge> getRandomList(String[] categoryIdList) {
        return randomChallengeRepository.findRandomByCategory(categoryIdList);
    }

}
