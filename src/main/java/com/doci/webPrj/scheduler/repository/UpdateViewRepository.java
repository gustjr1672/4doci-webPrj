package com.doci.webPrj.scheduler.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.doci.webPrj.scheduler.entity.FreeUpdate;
import com.doci.webPrj.scheduler.entity.GroupStartUpdate;
import com.doci.webPrj.scheduler.entity.RandomChoiceUpdate;

@Mapper
public interface UpdateViewRepository {

    List<FreeUpdate> findAllFree();

    List<RandomChoiceUpdate> findAllRandom();

    List<GroupStartUpdate> findAllGroup();

}
