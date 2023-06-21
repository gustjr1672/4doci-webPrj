package com.doci.webPrj.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.repository.FriendRequestNotificationRepository;
import com.doci.webPrj.user.repository.FriendRequestRepository;
import com.doci.webPrj.user.repository.MemberRepository;

@Service
public class NotificationServiceImp implements NotificationService {

    @Autowired
    FriendRequestNotificationRepository repository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Override
    public List<Member> getRequest(int userId) {
        List<Member> list = new ArrayList<>();
        List<Integer> idList = repository.findList(userId);
        for (Integer id : idList) {
            Member member = memberRepository.findById(id);
            list.add(member);
        }
        return list;
    }

    @Override
    public void requestAccept(int userId, int memberId) {
        repository.delete(memberId, userId);
        friendRequestRepository.accept(userId, memberId);
    }

    @Override
    public void requestRefuse(int userId, int memberId) {
        repository.delete(memberId, userId);
        friendRequestRepository.delete(memberId, userId);
        friendRequestRepository.delete(userId, memberId);
    }

    @Override
    public void sendRequestNotice(int userId, int memberId) {
        repository.send(userId, memberId);
    }

    @Override
    public void deleteRequestNotice(int userId, int memberId) {
        repository.delete(userId, memberId);
    }

    @Override
    public boolean getNotiStatus(int userId) {
        boolean result = true;
        List<Integer> idList = repository.findList(userId);
        if (idList.size() == 0)
            result = false;
        return result;
    }

}
