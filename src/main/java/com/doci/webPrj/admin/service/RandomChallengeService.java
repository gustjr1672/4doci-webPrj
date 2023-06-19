package com.doci.webPrj.admin.service;

import java.util.List;

import com.doci.webPrj.admin.entity.Category;
import com.doci.webPrj.admin.entity.RandomChallenge;
import com.doci.webPrj.admin.entity.Unit;



public interface RandomChallengeService {

    void create(RandomChallenge randomChallenge);

    List<Category> getCategoryList();

    List<Unit> getUnitList();

}
