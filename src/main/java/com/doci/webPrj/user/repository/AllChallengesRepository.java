package com.doci.webPrj.user.repository;

import com.doci.webPrj.user.entity.AllChallenges;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AllChallengesRepository {
    List<AllChallenges> findAll(int id);

    AllChallenges findChallenge(String challengeId);

}
