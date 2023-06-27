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
    InvitationNotificationRepository repository;
    @Autowired
    InviNotificationViewRepository viewRepository;

    @Override
    public void sendRequestNotice(int challengeId, int userId, int memberId) {
        repository.send(challengeId, userId, memberId);
    }

    @Override
    public List<InvitationNotification> getInvite(int userId) {
       return viewRepository.getList(userId);
    }


}
