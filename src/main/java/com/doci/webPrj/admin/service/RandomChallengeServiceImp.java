package com.doci.webPrj.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.admin.entity.Category;
import com.doci.webPrj.admin.entity.RandomChallenge;
import com.doci.webPrj.admin.entity.Unit;
import com.doci.webPrj.common.repository.RandomChallengeRepository;


@Service
public class RandomChallengeServiceImp implements RandomChallengeService {
   @Autowired
   private RandomChallengeRepository randomChallengeRepository;

    @Override
    public void create(RandomChallenge randomChallenge) {
      randomChallengeRepository.create(randomChallenge);
    }

    @Override
    public List<Category> getCategoryList() {
       return randomChallengeRepository.getCategoryList();
    }
    @Override
    public List<Unit> getUnitList() {
       return randomChallengeRepository.getUnitList();
    }

}
