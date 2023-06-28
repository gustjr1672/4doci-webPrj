package com.doci.webPrj.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.InvitationNotification;
import com.doci.webPrj.user.repository.InviNotificationViewRepository;
import com.doci.webPrj.user.repository.InvitationNotificationRepository;
import com.doci.webPrj.user.repository.InvitationRepository;

@Service
public class InvitationNotificationServiceImp implements InvitationNotificationService
 {
    @Autowired
    InvitationNotificationRepository inviNotirepository;
    @Autowired
    InviNotificationViewRepository viewRepository;
    @Autowired
    InvitationRepository invirepository;

    @Override
    public void sendRequestNotice(int challengeId,int memberId) {
        inviNotirepository.send(challengeId, memberId);
    }

    @Override
    public List<InvitationNotification> getInvite(int userId) {
       return viewRepository.getList(userId);
    }

    @Override
    public void requestAccept(int userId, int challengeId) {
       inviNotirepository.delete(userId,challengeId);
       invirepository.update(userId,challengeId,"수락");
    }

    @Override
    public void requestRefuse(int userId, int challengeId) {
       inviNotirepository.delete(userId,challengeId);
       invirepository.update(userId,challengeId,"거절");
    }


}
