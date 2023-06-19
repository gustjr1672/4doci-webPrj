package com.doci.webPrj.common.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.doci.webPrj.admin.entity.Category;
import com.doci.webPrj.admin.entity.RandomChallenge;
import com.doci.webPrj.admin.entity.Unit;



@Repository
@Mapper
public interface RandomChallengeRepository {
    void create(@Param("r")RandomChallenge randomChallenge);
    List<Category> getCategoryList();
    List<Unit> getUnitList();
  
}
