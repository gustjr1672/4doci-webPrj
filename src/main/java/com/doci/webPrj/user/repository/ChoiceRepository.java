package com.doci.webPrj.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.doci.webPrj.user.entity.Choice;

@Mapper
public interface ChoiceRepository {

    void save(@Param("c") Choice choice);

    void delete(int id);

    void update(int id);

    void editVisibility(@Param("id") int id, @Param("visibility") boolean visibility);

}
