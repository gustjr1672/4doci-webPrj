package com.doci.webPrj.user.api.controller;

import java.util.List;

import com.doci.webPrj.user.entity.Comment;
import com.doci.webPrj.user.entity.CommentView;
import com.doci.webPrj.user.service.FeedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiCommunityController")
@RequestMapping("api/comments")
public class CommunityController {

    @Autowired
    FeedService feedService;

    @GetMapping("{id}")
    public List<CommentView> list(@PathVariable("id") int recordId) {

        List<CommentView> list = feedService.getCommentList(recordId);

        return list;
    }

    @PostMapping
    public List<CommentView> add(Comment comment) {

        feedService.add(comment);
        List<CommentView> list = feedService.getCommentList(comment.getPerformanceRecordsId());

        return list;

    }

    @DeleteMapping("{id}")
    public List<CommentView> delete(@PathVariable("id") int commentId) {

        Comment comment = feedService.getCommentById(commentId);
        int PerformanceRecordsId = comment.getPerformanceRecordsId();
        feedService.delete(commentId);
        List<CommentView> list = feedService.getCommentList(PerformanceRecordsId);

        return list;
    }

    @PutMapping
    public Comment edit(Comment comment) {

        feedService.edit(comment);
        Comment modifiedComment = feedService.getCommentById(comment.getId());

        return modifiedComment;
    }

}
