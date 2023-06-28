package com.doci.webPrj.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InvitationNotificationRepository {

    void send(@Param("challengeId") int challengeId, @Param("memberId") int memberId);

    void delete(@Param("userId") int userId, @Param("challengeId") int challengeId);
}