package com.doci.webPrj.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.doci.webPrj.user.entity.Invitation;

@Mapper
public interface InvitationRepository {

    void save(@Param("i") Invitation invitation);
    
}
