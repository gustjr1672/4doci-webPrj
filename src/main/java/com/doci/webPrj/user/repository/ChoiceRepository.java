package com.doci.webPrj.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.doci.webPrj.user.entity.Choice;

import java.time.LocalDate;

@Mapper
public interface ChoiceRepository {

    void save(@Param("c") Choice choice);

    void delete(int id);

    void update(@Param("id") int id, @Param("totalResult") String totalResult);

    void editVisibility(@Param("id") int id, @Param("visibility") boolean visibility);

    boolean getVisibility(int id);

    void updateStartDate(@Param("id") int challengeId, @Param("startDate") LocalDate startDate);

}
