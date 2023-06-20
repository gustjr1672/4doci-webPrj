package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.user.entity.Member;

public interface NotificationService {

    List<Member> getRequest(int userId);

    void requestAccept(int userId, String nickname);

    void requestRefuse(int id, String nickname);

    void sendRequestNotice(int userId, int memberId);
}
