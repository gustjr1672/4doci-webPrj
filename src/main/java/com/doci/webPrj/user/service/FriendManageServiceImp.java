package com.doci.webPrj.user.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.FriendRequest;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.repository.FriendRequestRepository;
import com.doci.webPrj.user.repository.MemberRepository;

@Service
public class FriendManageServiceImp implements FriendManageService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Override
    public List<Member> getListByNickname(String nickname, String userNickname) {

        return memberRepository.findListByNickname(nickname, userNickname);
    }

    @Override
    public String getRequestState(String nickname, int id) {
        Member friend = memberRepository.findByNickname(nickname);
        FriendRequest toRequest = friendRequestRepository.findById(friend.getId(), id);
        FriendRequest fromRequest = friendRequestRepository.findById(id, friend.getId());
        if (toRequest == null)
            return "요청";

        if (toRequest.isAccept() == false && fromRequest.isAccept() == true)
            return "요청취소";
        else
            return "친구";
    }

    @Override
    public List<Map<String, Object>> getNewMemberList(String nickname, String userNickname, MyUserDetails user) {
        List<Map<String, Object>> response = new ArrayList<>();
        List<Member> list = getListByNickname(nickname, userNickname);
        for (Member member : list) {
            Map<String, Object> memberResponse = new LinkedHashMap<>();
            String requestState = getRequestState(member.getNickname(), user.getId());
            if (!requestState.equals("친구")) {
                memberResponse.put("id", member.getId());
                memberResponse.put("name", member.getName());
                memberResponse.put("nickname", member.getNickname());
                memberResponse.put("profile", member.getProfileImage());
                memberResponse.put("state", requestState);
                response.add(memberResponse);
            }
        }

        return response;
    }

    @Override
    public void request(int memberId, int userId) {
        friendRequestRepository.sendRequest(memberId, userId);
        friendRequestRepository.createRequest(userId, memberId);

    }

    @Override
    public void cancel(int memberId, int userId) {
        friendRequestRepository.delete(memberId, userId);
        friendRequestRepository.delete(userId, memberId);
    }

}
