package com.doci.webPrj.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GroupStartRepository {

    void save(@Param("memberId") int userId, @Param("challengeId") int challengeId);

    int getGroupChallengeId(int groupStartId);

}
