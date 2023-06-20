package com.doci.webPrj.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.repository.FriendRequestNotificationRepository;
import com.doci.webPrj.user.repository.MemberRepository;

@Service
public class NotificationServiceImp implements NotificationService {

    @Autowired
    FriendRequestNotificationRepository friendRequestNotificationRepository;
    @Autowired
    MemberRepository memberRepository;

    @Override
    public List<Member> getRequest(int userId) {
        List<Member> list = new ArrayList<>();
        List<Integer> idList = friendRequestNotificationRepository.findList(userId);
        for (Integer id : idList) {
            Member member = memberRepository.findById(id);
            list.add(member);
        }
        return list;
    }

    @Override
    public void requestAccept(int userId, String nickname) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'requestAccept'");
    }

    @Override
    public void requestRefuse(int id, String nickname) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'requestRefuse'");
    }

    @Override
    public void sendRequestNotice(int userId, int memberId) {
        friendRequestNotificationRepository.send(userId, memberId);
    }

}
