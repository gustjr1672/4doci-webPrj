package com.doci.webPrj.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.Comment;
import com.doci.webPrj.user.entity.Feed;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.repository.CommentRepository;
import com.doci.webPrj.user.repository.FeedRepository;

@Service
public class FeedServiceImp implements FeedService {

    @Autowired
    FeedRepository feedRepository;
    @Autowired
    CommentRepository commentRepository;

    @Override
    public Map<Feed, List<Comment>> getFeedList(List<Member> friendList, int userId) {
        List<Integer> members = new ArrayList<>();
        members.add(userId);
        for (Member friend : friendList) {
            members.add(friend.getId());
        }
        List<Feed> feedList = feedRepository.findFeedList(members);
        Map<Feed, List<Comment>> feeds = new HashMap<>();
        for (Feed feed : feedList) {
            int recordId = feed.getId();
            List<Comment> comments = commentRepository.findByRecordId(recordId);
            feeds.put(feed, comments);
        }
        return feeds;
    }

}
