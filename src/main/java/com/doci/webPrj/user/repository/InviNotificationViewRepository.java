package com.doci.webPrj.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.doci.webPrj.user.entity.InvitationNotification;

@Mapper
public interface InviNotificationViewRepository {
    List<InvitationNotification> getList(int userId);
}
