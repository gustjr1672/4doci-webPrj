package com.doci.webPrj.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.doci.webPrj.user.entity.FreeChallenge;

import java.time.LocalDate;

@Mapper
public interface FreeChallengeRepository {

    void save(@Param("f") FreeChallenge freeChallenge);

    void delete(int id);

    void update(@Param("id") int id, @Param("totalResult") String totalResult);

    void editVisibility(@Param("id") int id, @Param("visibility") boolean visibility);

    boolean getVisibility(int id);

    void updateStartDate(@Param("id") int challengeId, @Param("startDate")LocalDate startDate);

}
