package com.doci.webPrj.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.Invitation;
import com.doci.webPrj.user.entity.InvitationNotification;
import com.doci.webPrj.user.repository.GroupStartRepository;
import com.doci.webPrj.user.repository.InviNotificationViewRepository;
import com.doci.webPrj.user.repository.InvitationNotificationRepository;
import com.doci.webPrj.user.repository.InvitationRepository;

@Service
public class InvitationServiceImp implements InvitationService {

    @Autowired
    GroupStartRepository groupStartRepository;
    @Autowired
    InvitationNotificationRepository inviNotirepository;
    @Autowired
    InviNotificationViewRepository viewRepository;
    @Autowired
    InvitationRepository invirepository;

    private static final Invitation invitation = new Invitation();
    @Override
    public void invite(List<Integer> friendList,int challengeId) {
        invitation.setGroupChallengeId(challengeId);
        
        for(int friendId : friendList){
            invitation.setToMemberId(friendId);
            invirepository.save(invitation,null);
            inviNotirepository.send(challengeId, friendId);
        } 
    }
   @Override
    public void inviteLeader(int challengeId,int userId) {
        invitation.setGroupChallengeId(challengeId);
        invitation.setToMemberId(userId);
        invirepository.save(invitation,"수락"); // 방장만 초대 insert
    }
    @Override
    public void requestAccept(int userId, int challengeId) {
       groupStartRepository.save(userId,challengeId);
       inviNotirepository.delete(userId,challengeId);
       invirepository.update(userId,challengeId,"수락");
    }

    @Override
    public void sendRequestNotice(int challengeId,int memberId) {
       // invite 로 보냄
        
    }

    @Override
    public List<InvitationNotification> getInvite(int userId) {
       return viewRepository.getList(userId);
    }

    @Override
    public void requestRefuse(int userId, int challengeId) {
       inviNotirepository.delete(userId,challengeId);
       invirepository.update(userId,challengeId,"거절");
    }

     @Override
    public void cancelInvitation(int userId, int challengeId) {
            invirepository.delete(userId,challengeId);
            inviNotirepository.delete(userId,challengeId);
        } 
      
    
}
