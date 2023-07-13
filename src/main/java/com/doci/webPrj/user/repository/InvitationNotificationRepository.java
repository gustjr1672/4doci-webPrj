package com.doci.webPrj.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InvitationNotificationRepository {

    void send(@Param("challengeId") int challengeId, @Param("memberId") int memberId);

    List<Integer> findList(int userId);

    void delete(@Param("userId") int userId, @Param("challengeId") int challengeId);

    void deleteAll(int challengeId);
}