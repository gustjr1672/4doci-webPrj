package com.doci.webPrj.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GroupStartRepository {

    void save(@Param("memberId") int userId, @Param("challengeId") int challengeId);

    int getGroupChallengeId(int groupStartId);

    List<Integer> findList(int challengeId);

    void delete(@Param("memberId") int userId, @Param("challengeId") int challengeId);

    void update(@Param("id") int id, @Param("result") String groupStartTotalResult);

    int getId(@Param("groupChallengeId") int groupChallengeId, @Param("memberId") int memberId);

    void deleteById(int id);
}
