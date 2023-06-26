package com.doci.webPrj.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.doci.webPrj.user.entity.Feed;

@Mapper
public interface FeedRepository {

    List<Feed> findFeedList(List<Integer> members);

}
