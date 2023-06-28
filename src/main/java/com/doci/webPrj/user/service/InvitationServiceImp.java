package com.doci.webPrj.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.Invitation;
import com.doci.webPrj.user.repository.GroupStartRepository;
import com.doci.webPrj.user.repository.InvitationRepository;

@Service
public class InvitationServiceImp implements InvitationService {

@Autowired
    InvitationRepository invitationRepository;
@Autowired
    GroupStartRepository groupStartRepository;
    @Override
    public void invite(Invitation invitation) {
        invitationRepository.save(invitation);
    }

    @Override
    public void requestAccept(int userId, int challengeId) {
       groupStartRepository.save(userId,challengeId);
    }
}
