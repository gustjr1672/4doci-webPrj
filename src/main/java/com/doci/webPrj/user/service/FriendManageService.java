package com.doci.webPrj.user.service;

import java.util.List;
import java.util.Map;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.Member;

public interface FriendManageService {

    List<Member> getListByNickname(String nickname, String userNickname);

    String getRequestState(String nickname, int id);

    List<Map<String, Object>> getNewMemberList(String nickname, String string, MyUserDetails user);

    void request(int memberId, int userId);
}
