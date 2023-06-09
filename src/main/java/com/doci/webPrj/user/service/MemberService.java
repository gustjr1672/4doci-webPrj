package com.doci.webPrj.user.service;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.Member;
import java.util.Optional;

public interface MemberService {

    Optional<Member> findOne(String userId);

    void updateNickname(MyUserDetails user, String nickname);

    void updatePassword(MyUserDetails user, String password);
}
