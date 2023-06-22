package com.doci.webPrj.user.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.FreeChallenge;
import com.doci.webPrj.user.repository.FreeChallengeRepository;

@Service
public class StartChallengeServiceImp implements StartChallengeService {

    @Autowired
    FreeChallengeRepository freeChallengeRepository;

    public void addFreeChallenge(FreeChallenge freeChallenge) {
        LocalDate startDate = freeChallenge.getStartDate();
        LocalDate endDate = freeChallenge.getEndDate();
        Period period = startDate.until(endDate);

        int days = period.getDays();

        freeChallenge.setPeriod(days);
        freeChallengeRepository.save(freeChallenge);
    }

}
