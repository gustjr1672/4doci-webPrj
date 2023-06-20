package com.doci.webPrj.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FriendRequestNotificationRepository {

    void send(@Param("userId") int userId, @Param("memberId") int memberId);

    List<Integer> findList(int userId);

}
