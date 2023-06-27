package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.user.entity.InvitationNotification;

public interface InvitationNotificationService {


    void sendRequestNotice(int challengeId, int userId, int memberId);

    List<InvitationNotification> getInvite(int userId);
}
