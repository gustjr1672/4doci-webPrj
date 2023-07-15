package com.doci.webPrj.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.doci.webPrj.user.entity.Comment;
import com.doci.webPrj.user.entity.CommentNotificationView;

@Mapper
public interface CommentNotificationRepository {

    void insert(@Param("comment") Comment comment);

    List<CommentNotificationView> getList(int toMemberId);

    void delete(int notoficationId);

    int deleteAll(List<CommentNotificationView> list);

}
