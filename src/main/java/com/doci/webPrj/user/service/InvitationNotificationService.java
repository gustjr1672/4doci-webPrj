package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.user.entity.InvitationNotification;

public interface InvitationNotificationService {


    void sendRequestNotice(int challengeId, int memberId);

    List<InvitationNotification> getInvite(int userId);

    void requestAccept(int userId, int challengeId);

    void requestRefuse(int userId, int challengeId);
}
