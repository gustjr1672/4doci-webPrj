package com.doci.webPrj.scheduler.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.doci.webPrj.scheduler.entity.UpdateView;

@Mapper
public interface UpdateViewRepository {

    List<UpdateView> findAllFree();

    List<UpdateView> findAllRandom();

    List<UpdateView> findAllGroup();

}
