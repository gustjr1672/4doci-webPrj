package com.doci.webPrj.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InvitationNotificationRepository {

    void send(@Param("groupChallengeId") int challengeId, @Param("userId") int userId, @Param("memberId") int memberId);

}