package com.doci.webPrj.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.doci.webPrj.user.entity.Invitation;

@Mapper
public interface InvitationRepository {

    void save(@Param("i") Invitation invitation,@Param("isAccept") String isAccept);

    void update(@Param("userId") int userId, @Param("challengeId") int challengeId,
                @Param("isAccept") String isAccept);
                
    void delete(@Param("userId") int userId, @Param("challengeId") int challengeId);

    void deleteAll(int challengeId);

}
