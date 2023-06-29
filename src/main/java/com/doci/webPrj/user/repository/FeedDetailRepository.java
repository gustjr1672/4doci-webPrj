package com.doci.webPrj.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.doci.webPrj.user.entity.FeedDetail;

@Mapper
public interface FeedDetailRepository {

    List<FeedDetail> findFreeById(int fcId);

    List<FeedDetail> findRandomById(int chId);

    List<FeedDetail> findGroupById(int gsId);

}
