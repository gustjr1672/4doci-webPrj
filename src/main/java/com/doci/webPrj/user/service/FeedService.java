package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.user.entity.Comment;
import com.doci.webPrj.user.entity.CommentView;
import com.doci.webPrj.user.entity.Feed;
import com.doci.webPrj.user.entity.FeedDetail;
import com.doci.webPrj.user.entity.Member;

public interface FeedService {

    List<Feed> getFeedList(List<Member> friendList, int userId);

    List<FeedDetail> getFreeFeedList(int fcId);

    List<FeedDetail> getRandomFeedList(int chId);

    List<FeedDetail> getGroupFeedList(int gsId);

    List<CommentView> getCommentList(int recordId);

    void add(Comment comment);

    void delete(int commentId);

    Comment getCommentById(int commentId);

    void edit(Comment comment);

}
