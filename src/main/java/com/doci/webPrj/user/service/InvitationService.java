package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.user.entity.InvitationNotification;

public interface InvitationService {
   void invite(List<Integer> friendList,int challengeId);

   void requestAccept(int userId, int challengeId);

   void sendRequestNotice(int challengeId, int memberId);

    List<InvitationNotification> getInvite(int userId);

    void inviteLeader(int challengeId,int userId);

    void requestRefuse(int userId, int challengeId);
    
    void cancelInvitation(int userId, int challengeId);
}
