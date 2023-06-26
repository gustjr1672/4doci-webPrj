package com.doci.webPrj.user.service;

import java.util.List;
import java.util.Map;

import com.doci.webPrj.user.entity.Comment;
import com.doci.webPrj.user.entity.Feed;
import com.doci.webPrj.user.entity.Member;

public interface FeedService {

    Map<Feed, List<Comment>> getFeedList(List<Member> friendList, int userId);

}
