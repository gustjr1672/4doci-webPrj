package com.doci.webPrj.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.doci.webPrj.user.entity.FriendRequest;

@Repository
@Mapper
public interface FriendRequestRepository {

    void sendRequest(@Param("friendId") int friendId, @Param("userId") int userId);

    void createRequest(@Param("userId") int userId, @Param("friendId") int friendId);

    FriendRequest findById(@Param("id1") int id1, @Param("id2") int id2);

    void delete(@Param("id1") int id1, @Param("id2") int id2);

    List<FriendRequest> findRequestByUserId(int userId);

    void accept(@Param("userId") int userId, @Param("memberId") int memberId);

    List<FriendRequest> findFriendById(int userId);

}
