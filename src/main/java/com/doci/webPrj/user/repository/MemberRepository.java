package com.doci.webPrj.user.repository;

import com.doci.webPrj.user.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberRepository {
    Optional<Member> findById(String userId);

    Member findByNickname(String nickname);

    List<Member> findListByNickname(@Param("nickname") String nickname, @Param("userNickname") String userNickname);
}
