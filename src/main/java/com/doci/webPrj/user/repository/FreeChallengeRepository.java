package com.doci.webPrj.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.doci.webPrj.user.entity.FreeChallenge;

@Mapper
public interface FreeChallengeRepository {

    void save(@Param("f") FreeChallenge freeChallenge);

}