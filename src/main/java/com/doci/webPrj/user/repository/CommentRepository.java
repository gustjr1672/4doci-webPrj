package com.doci.webPrj.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.doci.webPrj.user.entity.Comment;
import com.doci.webPrj.user.entity.CommentView;

@Mapper
public interface CommentRepository {

    List<CommentView> findViewByRecordId(int recordId);

    void insert(@Param("comment") Comment comment);

}
