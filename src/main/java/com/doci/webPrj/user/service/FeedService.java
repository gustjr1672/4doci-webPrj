package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.user.entity.Feed;
import com.doci.webPrj.user.entity.Member;

public interface FeedService {

    List<Feed> getFeedList(List<Member> friendList, int userId);

}
