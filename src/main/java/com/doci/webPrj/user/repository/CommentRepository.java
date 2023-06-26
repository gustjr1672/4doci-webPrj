package com.doci.webPrj.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.doci.webPrj.user.entity.Comment;

@Mapper
public interface CommentRepository {

    List<Comment> findByRecordId(int recordId);

}
