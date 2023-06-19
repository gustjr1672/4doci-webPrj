package com.doci.webPrj.config;

import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserDetailService implements UserDetailsService {

    private final MemberService memberService;

    @Autowired
    public MyUserDetailService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String insertUserId) throws UsernameNotFoundException {
        Optional<Member> findOne = memberService.findOne(insertUserId);
        Member member = findOne.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다"));

        MyUserDetails details = MyUserDetails.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .name(member.getName())
                .email(member.getEmail())
                .pwd(member.getPwd())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .roles(member.getRoles())
                .build();

        return details;
    }
}
