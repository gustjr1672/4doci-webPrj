package com.doci.webPrj.user.api.controller;

import java.util.List;

import com.doci.webPrj.user.entity.Comment;
import com.doci.webPrj.user.entity.CommentView;
import com.doci.webPrj.user.service.FeedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiCommunityController")
@RequestMapping("comment")
public class CommunityController {

    @Autowired
    FeedService feedService;

    @GetMapping
    public List<CommentView> list(@RequestParam("rid") int recordId) {

        List<CommentView> list = feedService.getCommentList(recordId);

        return list;
    }

    @PostMapping
    public List<CommentView> add(Comment comment) {

        feedService.addComment(comment);
        List<CommentView> list = feedService.getCommentList(comment.getPerformanceRecordsId());

        return list;

    }

}
