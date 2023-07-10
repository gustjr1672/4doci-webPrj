package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.user.entity.CommentNotificationView;

public interface CommentNotificationService {

    List<CommentNotificationView> getList(int toMemberId);

}
