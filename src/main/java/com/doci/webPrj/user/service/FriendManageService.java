package com.doci.webPrj.user.service;

import java.util.List;
import java.util.Map;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.entity.OngoingChallengeView;
import com.doci.webPrj.user.entity.PastChallengeView;

public interface FriendManageService {

    List<Member> getListByNickname(String nickname, String userNickname);

    String getRequestState(String nickname, int id);

    List<Map<String, Object>> getNewMemberList(String nickname, String string, MyUserDetails user);

    void request(int memberId, int userId);

    void cancel(int memberId, int userId);

    List<Member> getFriendList(int userId);

    List<Member> getFriendListByNickname(int id, String nickname);

    void delete(int memberId, int id);

    List<OngoingChallengeView> getOngoingList(int id);

    List<PastChallengeView> getPastList(int id);

    Member getFriendById(int id);
}
