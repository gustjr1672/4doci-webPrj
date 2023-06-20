package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.user.entity.Member;

public interface NotificationService {

    List<Member> getRequest(int userId);

    void requestAccept(int userId, int memberId);

    void requestRefuse(int userId, int memberId);

    void sendRequestNotice(int userId, int memberId);

    void deleteRequestNotice(int userId, int memberId);
}
