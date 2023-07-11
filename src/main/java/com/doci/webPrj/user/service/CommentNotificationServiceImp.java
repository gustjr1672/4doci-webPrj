package com.doci.webPrj.user.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.CommentNotificationView;
import com.doci.webPrj.user.repository.CommentNotificationRepository;

@Service
public class CommentNotificationServiceImp implements CommentNotificationService {
    @Autowired
    CommentNotificationRepository commentNotificationRepository;

    @Override
    public List<CommentNotificationView> getList(int toMemberId) {

        List<CommentNotificationView> list = commentNotificationRepository.getList(toMemberId);

        LocalDateTime currenTime = LocalDateTime.now();
        for (CommentNotificationView commentNoti : list) {
            Timestamp commentTime = commentNoti.getRegDate();
            LocalDateTime commentDateTime = commentTime.toLocalDateTime();
            long minutesDiff = ChronoUnit.MINUTES.between(commentDateTime, currenTime);

            if (minutesDiff < 1) {
                commentNoti.setTimeMessage("방금 전");
            } else if (minutesDiff < 60) {
                commentNoti.setTimeMessage(minutesDiff + "분 전");
            } else if (minutesDiff < 1440) {
                long hoursDiff = minutesDiff / 60;
                commentNoti.setTimeMessage(hoursDiff + "시간 전");
            } else {
                long daysDiff = minutesDiff / 1440;
                commentNoti.setTimeMessage(daysDiff + "일 전");
            }
        }

        return list;
    }

}
