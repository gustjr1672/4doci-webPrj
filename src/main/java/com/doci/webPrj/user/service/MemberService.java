package com.doci.webPrj.user.service;

import com.doci.webPrj.user.entity.Member;
import java.util.Optional;

public interface MemberService {

    Optional<Member> findOne(String userId);

}
