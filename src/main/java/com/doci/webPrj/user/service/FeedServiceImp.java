package com.doci.webPrj.user.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.Feed;
import com.doci.webPrj.user.entity.FeedDetail;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.repository.CommentRepository;
import com.doci.webPrj.user.repository.FeedDetailRepository;
import com.doci.webPrj.user.repository.FeedRepository;

@Service
public class FeedServiceImp implements FeedService {

    @Autowired
    FeedRepository feedRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    FeedDetailRepository feedDetailRepository;

    @Override
    public List<Feed> getFeedList(List<Member> friendList, int userId) {
        List<Integer> members = new ArrayList<>();
        members.add(userId);
        for (Member friend : friendList) {
            members.add(friend.getId());
        }
        List<Feed> feedList = feedRepository.findFeedList(members);

        LocalDateTime currenTime = LocalDateTime.now();
        for (Feed feed : feedList) {
            Timestamp feedTime = feed.getRegDate();
            LocalDateTime feedDateTime = feedTime.toLocalDateTime();
            long minutesDiff = ChronoUnit.MINUTES.between(feedDateTime, currenTime);

            if (minutesDiff < 1) {
                feed.setTimeMessage("방금 전");
            } else if (minutesDiff < 60) {
                feed.setTimeMessage(minutesDiff + "분 전");
            } else if (minutesDiff < 1440) {
                long hoursDiff = minutesDiff / 60;
                feed.setTimeMessage(hoursDiff + "시간 전");
            } else {
                long daysDiff = minutesDiff / 1440;
                feed.setTimeMessage(daysDiff + "일 전");
            }
        }
        return feedList;
    }

    @Override
    public List<FeedDetail> getFreeFeedList(int fcId) {

        return feedDetailRepository.findFreeById(fcId);
    }

    @Override
    public List<FeedDetail> getRandomFeedList(int chId) {
        return feedDetailRepository.findRandomById(chId);
    }

    @Override
    public List<FeedDetail> getGroupFeedList(int gsId) {
        return feedDetailRepository.findGroupById(gsId);
    }

}