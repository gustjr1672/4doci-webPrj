package com.doci.webPrj.user.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import com.doci.webPrj.user.entity.GroupChallenge;

@Mapper
public interface GroupChallengeRepository {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(@Param("g") GroupChallenge groupChallenge);

    int getId(@Param("g") GroupChallenge groupChallenge);

    void delete(int challengeId);

    GroupChallenge findById(int challengeId);

    void update(int id);

    List<GroupChallenge> getTodayStartList(LocalDate currentDate);
}
