package com.doci.webPrj.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GroupStartRepository {

    void save(@Param("memberId") int userId, @Param("challengeId") int challengeId);

    int getGroupChallengeId(int groupStartId);

    List<Integer> findList(int challengeId);
}
