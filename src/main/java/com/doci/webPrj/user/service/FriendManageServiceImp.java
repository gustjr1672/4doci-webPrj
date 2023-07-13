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
import com.doci.webPrj.user.entity.OngoingChallengeView;
import com.doci.webPrj.user.entity.PastChallengeView;
import com.doci.webPrj.user.repository.ChallengeViewRepository;
import com.doci.webPrj.user.repository.FriendRequestRepository;
import com.doci.webPrj.user.repository.MemberRepository;

@Service
public class FriendManageServiceImp implements FriendManageService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FriendRequestRepository friendRequestRepository;
    @Autowired
    private ChallengeViewRepository challengeViewRepository;

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

    @Override
    public List<Member> getFriendList(int userId) {
        List<FriendRequest> requestList = friendRequestRepository.findFriendById(userId);
        List<Member> friendList = new ArrayList<>();

        for (FriendRequest request : requestList) {
            int id = request.getFromMemberId();
            FriendRequest anotherRequest = friendRequestRepository.findById(id, userId);
            if (anotherRequest.isAccept()) {
                Member member = memberRepository.findById(request.getFromMemberId());
                friendList.add(member);
            }
        }
        return friendList;
    }

    @Override
    public List<Member> getFriendListByNickname(int userId, String nickname) {
        List<FriendRequest> requestList = friendRequestRepository.findFriendById(userId);
        List<Member> friendList = new ArrayList<>();

        for (FriendRequest request : requestList) {
            int id = request.getFromMemberId();
            FriendRequest anotherRequest = friendRequestRepository.findById(id, userId);
            if (anotherRequest.isAccept()) {
                Member member = memberRepository.findById(request.getFromMemberId());
                if (member.getNickname().contains(nickname))
                    friendList.add(member);
            }
        }
        return friendList;
    }

    @Override
    public void delete(int memberId, int id) {
        friendRequestRepository.delete(memberId, id);
        friendRequestRepository.delete(id, memberId);
    }

    @Override
    public List<OngoingChallengeView> getOngoingList(int id, int userId) {
        List<OngoingChallengeView> list = null;
        if (id == userId)
            list = challengeViewRepository.getUserOngoingList(userId);
        else
            list = challengeViewRepository.getOngoingList(id);
        return list;
    }

    @Override
    public List<PastChallengeView> getPastList(int id, int userId) {
        List<PastChallengeView> list = null;
        if (id == userId)
            list = challengeViewRepository.getUserPastList(userId);
        else
            list = challengeViewRepository.getPastList(id);

        return list;
    }

    @Override
    public Member getFriendById(int id) {
        return memberRepository.findById(id);
    }

}
