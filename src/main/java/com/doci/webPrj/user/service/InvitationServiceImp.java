package com.doci.webPrj.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.Invitation;
import com.doci.webPrj.user.repository.InvitationRepository;

@Service
public class InvitationServiceImp implements InvitationService {

@Autowired
    InvitationRepository invitationRepository;

    @Override
    public void invite(Invitation invitation) {
        invitationRepository.save(invitation);
    }
}
