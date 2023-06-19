package com.doci.webPrj.user.repository;

import com.doci.webPrj.user.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberRepository {
    Optional<Member> findById(String userId);
}
