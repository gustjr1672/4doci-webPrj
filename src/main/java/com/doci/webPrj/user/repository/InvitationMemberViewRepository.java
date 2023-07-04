package com.doci.webPrj.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.doci.webPrj.user.entity.InvitationMember;

@Mapper
public interface InvitationMemberViewRepository {

    List<InvitationMember> findByChallengeId(int challengeId);
    
}
